package database

import "github.com/GuillaumeMZ/alter-backend/surrealhttp"

var Handle surrealhttp.SurrealHandle

func init() {
	Handle = surrealhttp.NewHandle("127.0.0.1", 8000)
	surrealhttp.Connect(&Handle, "root", "root")
	surrealhttp.UseNamespaceAndDb(&Handle, "test_ns", "test_db")
}
