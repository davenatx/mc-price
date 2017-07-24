package com.mcprice.snippet

import net.liftweb.http.DispatchSnippet
import net.liftweb.http.js.JE.JsRaw
import net.liftweb.http.js.JsCmds._
import net.liftweb.util.Helpers._

import scala.xml.Unparsed

object UtilSnippet extends DispatchSnippet {

  def dispatch = {
    case "year" ⇒ year
  }

  /* Set the year */
  def year = "#year" #> currentYear
}

object IEConditionalComments {
  /* 	<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries --> */
  def render = Unparsed("""<!--[if lt IE 9]>
	  <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
	  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script><![endif]-->""")
}

object GoogleAnalytics {
  def render = Script(JsRaw("""
    (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
    (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
    m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
    })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

    ga('create', 'UA-103177704-1', 'auto');
    ga('send', 'pageview');
    """))
}