package controller

import (
	"github.com/gin-gonic/gin"
	"github.com/golang-jwt/jwt"
	"github.com/gookit/color"
	"github.com/rs/xid"
	"net/http"
	"tbed-go/model"
	"tbed-go/utils"
	"time"
)

var SecretKey = []byte("9hUxqaGelNnCZaCW")

type ReqLogin struct {
	Username string `json:"username"`
	Password string `json:"password"`
}
type ReqReg struct {
	Username string `json:"username"`
	Password string `json:"password"`
}
type NewJwtClaims struct {
	*model.AdminUser
	jwt.StandardClaims
}

func Login(c *gin.Context) {
	user := &ReqLogin{}
	result := &model.Result{
		Code:    200,
		Message: "登录成功n",
		Data:    nil,
	}
	color.Cyan.Println("hhh")
	if err := c.ShouldBindJSON(user); err != nil {

		result.Message = "数据绑定失败"
		result.Code = http.StatusUnauthorized
		c.JSON(http.StatusUnauthorized, gin.H{
			"result": result,
		})
	}
	sqlU := model.QueryByUsername(user.Username)
	color.Red.Println(user.Username)
	color.Red.Println(user.Password)
	color.Red.Println("登陆接口进入")
	color.Cyan.Println(sqlU.Password)
	if sqlU.Password == utils.MD5(user.Password) {
		expiresTime := time.Now().Unix() + int64(60*60*24)
		//claims := jwt.StandardClaims{
		//	Audience:  user.Username,          // 受众
		//	ExpiresAt: expiresTime,            // 失效时间
		//	Id:        string(rune(user.Uid)), // 编号
		//	IssuedAt:  time.Now().Unix(),      // 签发时间
		//	Issuer:    sqlU.Username,            // 签发人
		//	NotBefore: time.Now().Unix(),      // 生效时间
		//	Subject:   "login",                // 主题
		//}
		stdClaims := jwt.StandardClaims{

			Audience:  "啊啊啊",             // 受众
			ExpiresAt: expiresTime,       // 失效时间
			Id:        "id",              // 编号
			IssuedAt:  time.Now().Unix(), // 签发时间
			Issuer:    "sqlU.Username",   // 签发人
			NotBefore: time.Now().Unix(), // 生效时间
			Subject:   "login",           // 主题
		}
		newClaims := NewJwtClaims{
			AdminUser:      &sqlU,
			StandardClaims: stdClaims,
		}
		tokenClaims := jwt.NewWithClaims(jwt.SigningMethodHS256, newClaims)
		if token, err := tokenClaims.SignedString(SecretKey); err == nil {
			result.Message = "登录成功"
			result.Data = token
			result.Code = http.StatusOK
			c.JSON(result.Code, result)
		} else {
			result.Message = "登录失败，请重新登陆"
			result.Code = http.StatusOK
			c.JSON(result.Code, gin.H{
				"result": result,
			})
		}
	} else {
		result.Message = "密码不一样"
		result.Code = http.StatusOK
		c.JSON(result.Code, gin.H{
			"result": result,
		})
	}
}

// Register @Summary 注册api
// @Description 描述信息
// @Tags accounts
// @Accept  json
// @Produce  json
// @Router /register [post]
func Register(c *gin.Context) {

	u := &model.AdminUser{}
	if err := c.ShouldBindJSON(u); err != nil {
		color.Danger.Println("json解析失败")
	}
	color.Redln(u)
	have := model.GetUserCheck(u.Username)
	if !have {
		u.Isok = true
		u.CreateTime = time.Now()
		u.UpdateTime = time.Now()
		u.Uid = xid.New()
		u.Birthday = time.Now()
		model.SaveUser(u)

		c.JSON(http.StatusOK, gin.H{
			"status":  200,
			"message": "注册成功",
		})

	} else {
		c.JSON(http.StatusOK, gin.H{
			"status":  2010,
			"message": "用户信息已存在，请确认后输入！",
		})
	}
}

// Index 通过token获取user信息
func Index(c *gin.Context) {
	userContext, exist := c.Get("user")
	if !exist {
		color.Danger.Println("失败了")
	}
	//查询用户组及该组的功能权限
	user, ok := userContext.(model.AdminUser) //这个是类型推断,判断接口是什么类型
	if !ok {

		color.Danger.Println("断言失败")
	}
	color.Red.Println(c.Request.Host)
	utils.JSON(c, 200, "获取成功", user)
}
func CheckToken(c *gin.Context) {
	userContext, exist := c.Get("user")
	if !exist {
		color.Danger.Println("失败了")
	}
	//查询用户组及该组的功能权限
	user, ok := userContext.(model.AdminUser) //这个是类型推断,判断接口是什么类型
	if !ok {

		color.Danger.Println("断言失败")
	}
	color.Red.Println(c.Request.Host)
	utils.JSON(c, 200, "获取成功", user)
}
