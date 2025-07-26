package middleware

import (
	"errors"
	"fmt"
	"net/http"
)

func GetToken(request *http.Request) (string, error) {
	tokenField := request.Header.Get("Authorization")
	if tokenField == "" {
		return "", errors.New("no authorization field found")
	}

	var token string

	_, err := fmt.Sscanf(tokenField, "Bearer %s", &token)
	if err != nil {
		return "", errors.New("unable to parse the autorization field")
	}

	return token, nil
}
