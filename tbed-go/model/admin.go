package model

import (
	"github.com/gookit/color"
)

type AdminUser struct {
	Id         uint   `json:"id"  gorm:"primaryKey"`
	Username   string `json:"username"`
	Password   string `json:"password"`
	Salt       string `json:"salt"`
	Email      string `json:"email"`
	Birthday   string `json:"birthday"`
	Level      string `json:"level"`
	Uid        string `json:"uid"`
	Isok       string `json:"isok"`
	Memory     string `json:"memory"`
	GroupId    string `json:"group_id"`
	CreateTime string `json:"create_time"`
	UpdateTime string `json:"update_time"`
}

func QueryByUsername(username string) (result AdminUser) {

	db := GetDb()
	db.Table("admin_user").Where("username = ?", username).First(&result)
	color.Red.Println(result)
	return

}
func SaveUser(data map[string]interface{}) {
	db := GetDb()
	db.Table("admin_user").Create(data)

}
func GetUserCheck(username string) bool {
	db := GetDb()
	obj := db.Table("admin_user").
		Where("username = ?  ", username).
		Where("status in (?)", []string{"1", "2"})
	var count int64
	obj.Count(&count)

	if count > 0 {
		return true
	} else {
		return false
	}
}
func QueryCheckToken() {

}
