package main

import (
	"net/http"

	"github.com/GuillaumeMZ/alter-backend/middleware"
	"github.com/GuillaumeMZ/alter-backend/song"
)

// func main() {
// 	handle := surrealhttp.NewHandle("127.0.0.1", 8000)
// 	err := surrealhttp.Connect(&handle, "root", "root")
// 	if err != nil {
// 		panic(err)
// 	}
// 	surrealhttp.UseNamespaceAndDb(&handle, "test_ns", "test_db")

// 	type result struct {
// 		Id string
// 	}

// 	results, err := surrealhttp.RunSqlQuery[result](handle, "select * from user where name = $name", []surrealhttp.QueryParameter{{Key: "name", Value: "john"}})
// 	if err != nil {
// 		panic(err)
// 	}

// 	for _, result := range results {
// 		fmt.Println(result.Result[1].Id)
// 	}
// }

func main() {
	http.HandleFunc("POST /songs", middleware.Authenticate(song.FindByFilter))

	err := http.ListenAndServe(":3333", nil)
	if err != nil {
		panic(err)
	}
}
