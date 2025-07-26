package middleware

import (
	"fmt"
	"net/http"

	"github.com/GuillaumeMZ/alter-backend/database"
	"github.com/GuillaumeMZ/alter-backend/surrealhttp"
)

func Authenticate(initialHandler http.HandlerFunc) http.HandlerFunc {
	return func(responseWriter http.ResponseWriter, request *http.Request) {
		token, err := GetToken(request)
		if err != nil {
			responseWriter.WriteHeader(http.StatusUnauthorized)
			return
		}

		sqlQuery := "SELECT count() FROM users WHERE token_=\"" + token + "\""

		type countResult struct {
			Count int `json:"count"`
		}

		queryResult, err := surrealhttp.RunSingleSqlQuery[countResult](database.Handle, sqlQuery, nil)
		if err != nil {
			fmt.Println(err.Error())
			responseWriter.WriteHeader(http.StatusInternalServerError)
			return
		}

		if len(queryResult) == 0 || queryResult[0].Count == 0 {
			responseWriter.WriteHeader(http.StatusUnauthorized)
			return
		}

		initialHandler(responseWriter, request)
	}
}
