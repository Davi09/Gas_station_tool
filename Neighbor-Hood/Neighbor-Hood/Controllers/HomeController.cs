using System;
using System.Collections.Generic;
using System.Linq;
using System.Web.Mvc;
using YelpSharp;
using Google;
using Neighbor_Hood.Models;
using Facebook;
namespace Neighbor_Hood.Controllers
{
    public class HomeController : Controller
    {
        public SexOffenderState SoS = new SexOffenderState();
        public ApplicationDbContext db = new ApplicationDbContext();

        public string zillowKey = "X1-ZWz1dzcmokf0nf_39zrb";
        public string googleKey = "AIzaSyA06f28rEyXPzdRTA2_EFcUp2wzM5BkbnE";
        public ActionResult Index()
        {
            ViewData["states"] = new SelectList(db.States.Select(s => s.Code));
            return View();
        }

        public ActionResult Search(string address, string zip, string state)
        {
            //Yelp
            Yelp yl = new Yelp(Config.Options);
            var result = yl.Search("Restaurants", zip).Result;

            //Zillow
            var zillowData = ZillowAPI.GetZestimate(zillowKey, address, zip);

            //Google Map

            SoS.NYState(zip);
            
            return View();
        }
        public PartialViewResult YelpPartial()
        {
            return PartialView();
        }
        public PartialViewResult GoogleMap()
        {
            return PartialView();
        }
        public JsonResult YelpJson(string address, string zip, string state)
        {
            //Yelp
            List<YelpSharp.Data.SearchResults> allResults = new List<YelpSharp.Data.SearchResults>();
            Yelp yl = new Yelp(Config.Options);
            //Restaurants
            var restaurantResult = yl.Search("Restaurants", zip).Result;
            //Nightlife
            var nightlifeResult = yl.Search("nightlife", zip).Result;
            //Shopping
            var shoppingResult = yl.Search("shopping", zip).Result;
            //ActiveLife
            var activelifeResult = yl.Search("active", zip).Result;

            //allResults = restaurantResult.
            allResults.Add(restaurantResult);
            allResults.Add(nightlifeResult);
            allResults.Add(shoppingResult);
            allResults.Add(activelifeResult);
            var jsonYelp = Json(new { yelpData = allResults }, JsonRequestBehavior.AllowGet);
            return jsonYelp;
        }
        public JsonResult SexOffenderJson(string address, string zip, string state)
        {
            if(state == "NY")
            {
            var SOs = SoS.NYState(zip);
            var jsonSO = Json(new { soData = SOs }, JsonRequestBehavior.AllowGet);
            return jsonSO;
            }
            else
            {
                return null;
            }
        }
        public JsonResult ZillowJson(string address, string zip, string state)
        {
            //Zillow
            var zillowData = ZillowAPI.GetZestimate(zillowKey, address, zip);
            var jsonZillow = Json(new { zillowData = zillowData }, JsonRequestBehavior.AllowGet);
            return jsonZillow;
        }
        public JsonResult FacebookJson(string access, string uid)
        {
            var client = new FacebookClient(access);
            dynamic me = client.Get("me");
            string aboutMe = me.about;
            var friends = client.Get(uid+"/friends");
            //ApplicationUser user = User.Identity.;
            //var client = new FacebookClient(accessToken);
            dynamic result = client.Get("me", new { fields = "name,id" });

            return Json(new
            {
                id = result.id,
                name = result.name,
            });
        }
        public ActionResult UserInfo(string accessToken)
        {
            /*string myid = "10203548478246651";
            var accessTokens = "CAANkTknDBwgBAEzkl8fWdg5sPcGVM6WG7I3wjy92AZAsZA6TKYbw1QQZBxvAqLaPG6ZAk9ZCBUDZCx0rabJuWC7ZBjYppzniRvnTZAU6o940yy7ZAY7nZBUb2OniMzIOqX99WCjZCy3CxzrK1EZCCZBTGeLJfIosX5ps5dXBJJAIQqDHa7Bx6AZAZBKpD7ZARBhAMHZBIDWh8fgTuSQUbTp8uLDz6JN2F";
            var client = new FacebookClient(accessTokens);
            //client.
            dynamic me = client.Get("me");
            string aboutMe = me.about;
            //ApplicationUser user = User.Identity.;
            //var client = new FacebookClient(accessToken);
            dynamic result = client.Get("me", new { fields = "name,id" });

            return Json(new
            {
                id = result.id,
                name = result.name,
            });*/
            return null;
        }
    }
}
