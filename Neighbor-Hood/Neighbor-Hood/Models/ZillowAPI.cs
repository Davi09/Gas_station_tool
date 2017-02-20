using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Net;
using System.Xml;

namespace Neighbor_Hood.Controllers
{
   
/// <summary>
/// ZillowAPI implementation for .Net
/// </summary>
    public class ZillowAPI
    {
        public class ZillowEstimate
        {
            public int ReturnCode;
            public string ReturnCodeMessage;
            public int ZillowId;
            public string LinktoMap;
            public string LinktoHomeDetails;
            public string LinktoGraphsAndData;
            public string LinktoComparables;
            public decimal Estimate;
            public decimal RentEstimate;
            public DateTime LastUpdated;
            public decimal ValueChange;
            public int ValueChangeDurationInDays;
            public decimal ValueRangeLow;
            public decimal ValueRangeHigh;
            public decimal RentEstimateLow;
            public decimal RentEstimateHigh;
            public decimal Latitude;
            public decimal Longitude;
            public string Street;
            public string City;
            public string State;
            public string ZipCode;
        }


        public static ZillowEstimate GetZestimate(string zillowWebServiceId, string Address, string ZipCode)
        {
            //http://www.zillow.com/howto/api/GetSearchResults.htm

            var z = new ZillowEstimate();
            // Construct the url
            var zEstimate = new decimal();
            var url = String.Format("http://www.zillow.com/webservice/GetSearchResults.htm?zws-id={0}&address={1}&citystatezip={2}&rentzestimate={3}", zillowWebServiceId, Address, ZipCode,true);


            // Make the HTTP request / get the response
            var Request = (System.Net.HttpWebRequest)WebRequest.Create(url);
            var Response = (HttpWebResponse)Request.GetResponse();

            // Parse the HTTP response into an XML document
            XmlDocument xml = new XmlDocument();
            xml.Load(Response.GetResponseStream());
            XmlElement root = xml.DocumentElement;

            //Return Code
            z.ReturnCode = int.Parse(root.SelectSingleNode("//message/code").InnerText);
            z.ReturnCodeMessage = root.SelectSingleNode("//message/text").InnerText;


            if (z.ReturnCode == 0)
            {
                try
                {
                    z.ZillowId = int.Parse(root.SelectSingleNode("//response/results/result/zpid").InnerText);
                }
                catch
                {
                    z.ZillowId = -1;
                }
                try
                {
                    z.LinktoMap = root.SelectSingleNode("//response/results/result/links/mapthishome").InnerText;
                }
                catch
                {
                    z.LinktoMap = "";
                }
                try
                {
                    z.LinktoHomeDetails = root.SelectSingleNode("//response/results/result/links/homedetails").InnerText;
                }
                catch
                {
                    z.LinktoHomeDetails = "";
                }
                try { 
                z.LinktoGraphsAndData = root.SelectSingleNode("//response/results/result/links/graphsanddata").InnerText;
                }
                catch
                {
                    z.LinktoGraphsAndData = "";
                }
                try
                {

                
                    z.LinktoComparables = root.SelectSingleNode("//response/results/result/links/comparables").InnerText;
                }
                catch
                {
                    z.LinktoComparables = "";
                }
                try { 
                    z.Estimate = decimal.Parse(root.SelectSingleNode("//response/results/result/zestimate/amount").InnerText);
                }
                catch
                {
                    z.Estimate = 0;
                }
                try
                {
                    z.LastUpdated = DateTime.Parse(root.SelectSingleNode("//response/results/result/zestimate/last-updated").InnerText);
                }
                catch
                {
                    z.LastUpdated = DateTime.Today;
                }
                try
                {
                    z.ValueChange = decimal.Parse(root.SelectSingleNode("//response/results/result/zestimate/valueChange").InnerText);
                }
                catch
                {
                    z.ValueChange = 0;
                }
                try { 

                    z.ValueChangeDurationInDays = int.Parse(root.SelectSingleNode("//response/results/result/zestimate/valueChange").Attributes["duration"].Value);
                    }
                catch
                {
                    z.ValueChangeDurationInDays = 0;
                }
                try
                {
                    z.ValueRangeLow = decimal.Parse(root.SelectSingleNode("//response/results/result/zestimate/valuationRange/low").InnerText);
                }
                catch
                {
                    z.ValueRangeLow = 0;
                }
                try
                {
                    z.ValueRangeHigh = decimal.Parse(root.SelectSingleNode("//response/results/result/zestimate/valuationRange/high").InnerText);
                }
                catch
                {
                    z.ValueRangeHigh = 0;
                }
                z.Street = root.SelectSingleNode("//response/results/result/address/street").InnerText;
                z.City = root.SelectSingleNode("//response/results/result/address/city").InnerText;
                z.State = root.SelectSingleNode("//response/results/result/address/state").InnerText;
                z.ZipCode = root.SelectSingleNode("//response/results/result/address/zipcode").InnerText;
                z.Latitude = decimal.Parse(root.SelectSingleNode("//response/results/result/address/latitude").InnerText);
                z.Longitude = decimal.Parse(root.SelectSingleNode("//response/results/result/address/longitude").InnerText);
                z.RentEstimate = decimal.Parse(root.SelectSingleNode("//response/results/result/rentzestimate/amount").InnerText);
                z.RentEstimateLow = decimal.Parse(root.SelectSingleNode("//response/results/result/rentzestimate/valuationRange/low").InnerText);
                z.RentEstimateHigh = decimal.Parse(root.SelectSingleNode("//response/results/result/rentzestimate/valuationRange/high").InnerText);
            }
            Response.Close();

            return z;
        }

    }
}