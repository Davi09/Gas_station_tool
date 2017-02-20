using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net;
using System.Text;
using System.Text.RegularExpressions;
using System.Web;
using System.Xml;

namespace Neighbor_Hood.Models
{
    public class SexOffenderState
    {
        public string googleKey = "AIzaSyA06f28rEyXPzdRTA2_EFcUp2wzM5BkbnE";

        public class SexOffender
        {
            public string name;
            public string link;
            public string address;
            public string lat;
            public string lng;
        }

        public List<SexOffender> NYState(string zip)
        {
            string site = "http://www.criminaljustice.ny.gov";
            string requestString = "";
            List<SexOffender> SOList = new List<SexOffender>();
            if(zip != null)
            {
                requestString = site + "/SomsSUBDirectory/search_index.jsp?offenderSubmit=true&LastName=&County=&Zip=" + zip + "&Submit=Search";
            }
            string responseFromServer = WebResponse(requestString);
            //Extract Content
            string pat = "<td> <a (.*?)</td>";
            string linkpat = "href=\"(.*?)\"";
            string addresspat = "<li class=\"no-border-bottom\"><span class=\"label\">Address</span><span class=\"value\">(.*?)</span>";
            string namepat = ">(.*?)</a>";
            // Instantiate the regular expression object.
            Regex r = new Regex(pat, RegexOptions.IgnoreCase);
            Regex linkr = new Regex(linkpat, RegexOptions.IgnoreCase);
            Regex addressr = new Regex(addresspat, RegexOptions.IgnoreCase);
            Regex namer = new Regex(namepat, RegexOptions.IgnoreCase);
            Match m = r.Match(responseFromServer);
            while (m.Success)
            {
                string link = "";
                string name = "";
                string address = "";
                for (int i = 1; i <= 2; i++)
                {
                    Group g = m.Groups[i];
                    CaptureCollection cc = g.Captures;
                    for (int j = 0; j < cc.Count; j++)
                    {
                        Capture c = cc[j];
                        Match linkm = linkr.Match(c.Value);
                        Match namem = namer.Match(c.Value);
                        
                        name = namem.Groups[i].ToString();
                        link = site + linkm.Groups[i].ToString();
                        string subResponse = WebResponse(link);
                        Match addressm = addressr.Match(subResponse);
                        address = addressm.Groups[i].ToString();
                        string addressnoHTML = Regex.Replace(address, @"<[^>]+>|&nbsp;", " ").Trim();
                        //address.Substring(address.IndexOf())
                        var geo = GetGeoCoding(googleKey, addressnoHTML);
                        SexOffender so = new SexOffender();
                        so.address = addressnoHTML;
                        so.lat = geo[0];
                        so.lng = geo[1];
                        so.name = name;
                        so.link = link;
                        SOList.Add(so);
                    }
                }
                m = m.NextMatch();
            }
            // Clean up the streams.

            return SOList;
        }

        public string WebResponse(string requestString)
        {
            WebRequest request = WebRequest.Create(requestString);
            // Set the Method property of the request to POST.
            request.Method = "POST";
            // Create POST data and convert it to a byte array.
            string postData = "This is a test that posts this string to a Web server.";
            byte[] byteArray = Encoding.UTF8.GetBytes(postData);
            // Set the ContentType property of the WebRequest.
            request.ContentType = "application/x-www-form-urlencoded";
            // Set the ContentLength property of the WebRequest.
            request.ContentLength = byteArray.Length;
            // Get the request stream.
            Stream dataStream = request.GetRequestStream();
            // Write the data to the request stream.
            dataStream.Write(byteArray, 0, byteArray.Length);
            // Close the Stream object.
            dataStream.Close();
            // Get the response.
            WebResponse response = request.GetResponse();
            // Get the stream containing content returned by the server.
            dataStream = response.GetResponseStream();
            // Open the stream using a StreamReader for easy access.
            StreamReader reader = new StreamReader(dataStream);
            // Read the content.
            string responseFromServer = reader.ReadToEnd();

            reader.Close();
            dataStream.Close();
            response.Close();
            return responseFromServer;
        }

        public string[] GetGeoCoding(string googleAPI, string Address)
        {
            //http://www.zillow.com/howto/api/GetSearchResults.htm


            var url = String.Format("https://maps.googleapis.com/maps/api/geocode/xml?address={0}&key={1}", Address, googleAPI);

            string[] geo = new string[2];
            // Make the HTTP request / get the response
            var Request = (System.Net.HttpWebRequest)WebRequest.Create(url);
            var Response = (HttpWebResponse)Request.GetResponse();

            // Parse the HTTP response into an XML document
            XmlDocument xml = new XmlDocument();
            xml.Load(Response.GetResponseStream());
            XmlElement root = xml.DocumentElement;

            //Return Code
            //int ReturnCode = int.Parse(root.SelectSingleNode("//message/code").InnerText);
            string ReturnCodeMessage = root.SelectSingleNode("//status").InnerText;


            if (ReturnCodeMessage == "OK")
            {
                /*z.ZillowId = int.Parse(root.SelectSingleNode("//response/results/result/zpid").InnerText);
                z.LinktoMap = root.SelectSingleNode("//response/results/result/links/mapthishome").InnerText;
                z.LinktoHomeDetails = root.SelectSingleNode("//response/results/result/links/homedetails").InnerText;
                z.LinktoGraphsAndData = root.SelectSingleNode("//response/results/result/links/graphsanddata").InnerText;
                z.LinktoComparables = root.SelectSingleNode("//response/results/result/links/comparables").InnerText;
                z.Estimate = decimal.Parse(root.SelectSingleNode("//response/results/result/zestimate/amount").InnerText);
                z.LastUpdated = DateTime.Parse(root.SelectSingleNode("//response/results/result/zestimate/last-updated").InnerText);
                z.ValueChange = decimal.Parse(root.SelectSingleNode("//response/results/result/zestimate/valueChange").InnerText);
                z.ValueChangeDurationInDays = int.Parse(root.SelectSingleNode("//response/results/result/zestimate/valueChange").Attributes["duration"].Value);
                z.ValueRangeLow = decimal.Parse(root.SelectSingleNode("//response/results/result/zestimate/valuationRange/low").InnerText);
                z.ValueRangeHigh = decimal.Parse(root.SelectSingleNode("//response/results/result/zestimate/valuationRange/high").InnerText);

                z.Street = root.SelectSingleNode("//response/results/result/address/street").InnerText;
                z.City = root.SelectSingleNode("//response/results/result/address/city").InnerText;
                z.State = root.SelectSingleNode("//response/results/result/address/state").InnerText;
                z.ZipCode = root.SelectSingleNode("//response/results/result/address/zipcode").InnerText;
                z.Latitude = decimal.Parse(root.SelectSingleNode("//response/results/result/address/latitude").InnerText);
                z.Longitude = decimal.Parse(root.SelectSingleNode("//response/results/result/address/longitude").InnerText);
                z.RentEstimate = decimal.Parse(root.SelectSingleNode("//response/results/result/rentzestimate/amount").InnerText);
                z.RentEstimateLow = decimal.Parse(root.SelectSingleNode("//response/results/result/rentzestimate/valuationRange/low").InnerText);
                z.RentEstimateHigh = decimal.Parse(root.SelectSingleNode("//response/results/result/rentzestimate/valuationRange/high").InnerText);*/
                geo[0] = root.SelectSingleNode("//result/geometry/location/lat").InnerText;
                geo[1] = root.SelectSingleNode("//result/geometry/location/lng").InnerText;
            }
            Response.Close();


            return geo;
        }

    }
}