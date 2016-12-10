package controllers

import play.api.mvc._

class TestController extends Controller{

  def test = Action {
    Ok("test")
  }
}
