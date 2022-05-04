package model

type Article struct {
	Id                 int    `json:"id"`
	TypeId             int    `json:"type_id"`
	Title              string `json:"title"`
	ArticleContent     string `json:"article_content"`
	Introduce          string `json:"introduce"`
	AddTime            int    `json:"add_time"`
	ViewCount          int    `json:"view_count"`
	PartCount          int    `json:"part_count"`
	ArticleContentHtml string `json:"article_content_html"`
	IntroduceHtml      string `json:"introduce_html"`
	IsTop              int    `json:"is_top"`
}
