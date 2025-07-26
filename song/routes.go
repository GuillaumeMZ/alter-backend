package song

import (
	"bytes"
	"encoding/json"
	"io"
	"net/http"

	"github.com/GuillaumeMZ/alter-backend/database"
	"github.com/GuillaumeMZ/alter-backend/surrealhttp"
)

func FindByFilter(responseWriter http.ResponseWriter, request *http.Request) {
	body, err := io.ReadAll(request.Body)
	if err != nil {
		responseWriter.WriteHeader(http.StatusBadRequest)
		return
	}

	var filter SongFilter
	if err := json.Unmarshal(body, &filter); err != nil {
		responseWriter.WriteHeader(http.StatusBadRequest)
		return
	}

	sqlQuery := "SELECT id, album, artist, name FROM songs WHERE album CONTAINS $filter OR artist CONTAINS $filter OR name CONTAINS $filter"
	songs, err := surrealhttp.RunSingleSqlQuery[Song](database.Handle, sqlQuery, []surrealhttp.QueryParameter{{Key: "filter", Value: filter.Filter}})
	if err != nil {
		responseWriter.WriteHeader(http.StatusInternalServerError)
		return
	}

	var encodedSongs bytes.Buffer
	err = json.NewEncoder(&encodedSongs).Encode(songs)
	if err != nil {
		responseWriter.WriteHeader(http.StatusInternalServerError)
		return
	}

	responseWriter.WriteHeader(http.StatusOK)
	responseWriter.Write(encodedSongs.Bytes())
}
