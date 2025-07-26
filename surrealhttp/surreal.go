// Package surrealhttp provides functions to interact with a SurrealDB database through its HTTP interface.
package surrealhttp

import (
	"bytes"
	"encoding/base64"
	"encoding/json"
	"errors"
	"io"
	"net/http"
	"net/url"
	"strconv"
)

type SurrealHandle struct {
	url       string
	username  string //todo: replace username and password by the token returned in Connect()
	password  string
	namespace string
	database  string
}

// Creates a unconnected handle to a SurrealDB database.
func NewHandle(ip string, port int) SurrealHandle {
	return SurrealHandle{
		url: ip + ":" + strconv.Itoa(port),
	}
}

// Attempts to connect to a SurrealDB database. Returns nil if the connection attempt succeded; an error otherwise.
func Connect(handle *SurrealHandle, username string, password string) error {
	type LoginSettings struct {
		User string `json:"user"`
		Pass string `json:"pass"`
	}

	settings := LoginSettings{
		User: username,
		Pass: password,
	}

	var buf bytes.Buffer
	err := json.NewEncoder(&buf).Encode(settings)
	if err != nil {
		return err
	}

	url := "http://" + handle.url + "/signin"
	loginResponse, err := http.Post(url, "application/json", &buf)
	if err != nil {
		return err
	}

	defer loginResponse.Body.Close()

	if loginResponse.StatusCode != 200 {
		return errors.New("couldn't connect to database: http error code is " + strconv.Itoa(loginResponse.StatusCode))
	}

	handle.username = username
	handle.password = password

	return nil
}

// Selects a SurrealDB namespace and a SurrealDB database to use for subsequent operations.
func UseNamespaceAndDb(handle *SurrealHandle, namespace string, database string) {
	handle.namespace = namespace
	handle.database = database
}

type QueryParameter struct {
	Key   string
	Value string
}

type QueryResult[T any] struct {
	Result []T
	Status string
	Time   string
}

// Runs a Sql query against a database. Use ? to denote escaped query parameters, for instance:
// RunSqlQuery(handle, "select * from user where age > $age", []{QueryParameter{key: "age", value: "18"}})
func RunSqlQuery[T any](handle SurrealHandle, query string, args []QueryParameter) ([]QueryResult[T], error) {
	finalUrl := "http://" + handle.url + "/sql"

	values := url.Values{}

	if args != nil {
		for _, arg := range args {
			values.Add(arg.Key, arg.Value)
		}

		if len(args) != 0 {
			finalUrl += "?" + values.Encode()
		}
	}

	request, err := http.NewRequest(http.MethodPost, finalUrl, bytes.NewBufferString(query))
	if err != nil {
		return nil, err
	}

	encodedToken := base64.StdEncoding.EncodeToString([]byte(handle.username + ":" + handle.password))
	request.Header.Add("Accept", "application/json")
	request.Header.Add("Authorization", "Basic "+encodedToken)
	request.Header.Add("Surreal-DB", handle.database)
	request.Header.Add("Surreal-NS", handle.namespace)

	response, err := http.DefaultClient.Do(request)
	if err != nil {
		return nil, err
	}
	defer response.Body.Close()

	if response.StatusCode != 200 {
		return nil, errors.New("sql query failed: error code is " + strconv.Itoa(response.StatusCode))
	}

	body, err := io.ReadAll(response.Body)
	if err != nil {
		return nil, err
	}

	var results []QueryResult[T]
	if err = json.Unmarshal(body, &results); err != nil {
		return nil, err
	}

	return results, nil
}

func RunSingleSqlQuery[T any](handle SurrealHandle, query string, args []QueryParameter) ([]T, error) {
	results, err := RunSqlQuery[T](handle, query, args)
	if err != nil {
		return nil, err
	}

	return results[0].Result, nil
}
