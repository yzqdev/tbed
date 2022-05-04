package main

import (
	"github.com/gin-gonic/gin"
	"tbed-go/controller"
	"tbed-go/middleware"
)

func InitRouter(e *gin.Engine) {
	baseRouter := e.Group("/base")
	{
		baseRouter.POST("/login", controller.Login)
		baseRouter.POST("/reg", controller.Register)
	}
	pageRouter := e.Group("/")
	{
		pageRouter.GET("/", controller.IndexPage)
	}
	adminRouter := e.Group("/admin", middleware.JwtHandler())
	{

		adminRouter.GET("/index", controller.Index)
		adminRouter.POST("/addArticle", controller.AddArticle)
		adminRouter.PUT("/updateArticle", controller.UpdateArticle)
		adminRouter.DELETE("/delArticle/:id", controller.DelArticle)
		adminRouter.GET("/getArticleById/:id", controller.GetArticleById)
		adminRouter.GET("/checkToken", controller.CheckToken)

	}
	homeRouter := e.Group("/default")
	{
		homeRouter.GET("/index")
		homeRouter.GET("/getArticleById/:id", controller.GetArticleById)

	}

}
