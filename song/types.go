package song

type Song struct {
	Album  string `json:"album"`
	Artist string `json:"artist"`
	Id     string `json:"id"`
	Name   string `json:"name"`
}

type SongFilter struct {
	Filter string `json:"filter"`
}
