package bootstrap.liftweb

import net.liftweb._
import util._
import Helpers._

import common._
import http._
import sitemap._
import Loc._

import scala.xml.NodeSeq
import scala.language.postfixOps

/**
 * Lift's Boot class
 */
class Boot extends Loggable {
  def boot {

    // Snippet Code
    LiftRules.addToPackages("com.mcprice")

    // Set Sitemap
    LiftRules.setSiteMap(Site.sitemap)

    // Render / instead of /index
    SiteMap.rawIndex_? = true

    //Show the spinny image when an Ajax call starts
    LiftRules.ajaxStart =
      Full(() ⇒ LiftRules.jsArtifacts.show("ajax-loader").cmd)

    // Make the spinny image go away when it ends
    LiftRules.ajaxEnd =
      Full(() ⇒ LiftRules.jsArtifacts.hide("ajax-loader").cmd)

    // Force the request to be UTF-8
    LiftRules.early.append(_.setCharacterEncoding("UTF-8"))

    // Use HTML5 for rendering
    LiftRules.htmlProperties.default.set((r: Req) ⇒
      new Html5Properties(r.userAgent))

    // Content Security Policy
    LiftRules.securityRules = () ⇒ {
      SecurityRules(content = Some(ContentSecurityPolicy(
        fontSources = List(
          ContentSourceRestriction.Self,
          ContentSourceRestriction.Host("maxcdn.bootstrapcdn.com"),
          ContentSourceRestriction.Host("fonts.googleapis.com"),
          ContentSourceRestriction.Host("fonts.gstatic.com")
        ),
        scriptSources = List(
          ContentSourceRestriction.Self,
          ContentSourceRestriction.UnsafeEval,
          ContentSourceRestriction.UnsafeInline,
          ContentSourceRestriction.Host("ajax.googleapis.com"),
          ContentSourceRestriction.Host("www.google-analytics.com"),
          ContentSourceRestriction.Host("maxcdn.bootstrapcdn.com")
        ),
        styleSources = List(
          ContentSourceRestriction.Self,
          ContentSourceRestriction.UnsafeInline,
          ContentSourceRestriction.Host("maxcdn.bootstrapcdn.com"),
          ContentSourceRestriction.Host("fonts.googleapis.com"),
          ContentSourceRestriction.Host("fonts.gstatic.com")
        ),
        connectSources = List(
          ContentSourceRestriction.Scheme("blob"),
          ContentSourceRestriction.Self
        ),
        imageSources = List(
          ContentSourceRestriction.Scheme("blob"),
          ContentSourceRestriction.Self,
          ContentSourceRestriction.Host("www.google-analytics.com")
        ),
        defaultSources = List(
          ContentSourceRestriction.Scheme("blob"),
          ContentSourceRestriction.Self,
          ContentSourceRestriction.Host("www.google.com")
        )
      )))
    }

    // Custom 404 page
    LiftRules.uriNotFound.prepend(NamedPF("404handler") {
      case (req, failure) ⇒
        NotFoundAsTemplate(ParsePath(List("404"), "html", true, false))
    })
  }
}

// SiteMap
object Site {

  val our_story = Menu.i("Our Story") / "index" >> LocGroup("main")
  val our_wedding = Menu.i("Our Wedding") / "our-wedding" >> LocGroup("main")
  val hotel = Menu.i("Accommodations") / "accommodations" >> LocGroup("main")
  val registry = Menu.i("Registry") / "registry" >> LocGroup("main")

  def sitemap = SiteMap(
    our_story,
    our_wedding,
    hotel,
    registry
  )
}