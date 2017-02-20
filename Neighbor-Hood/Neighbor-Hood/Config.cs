using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using YelpSharp;
namespace Neighbor_Hood
{
    public class Config
    {
        private static Options _options;
        /// <summary>
        /// return the oauth options for using the Yelp API. I store my keys in the environment settings, but you
        /// can just write them out here, or put them into an app.config file. For more info, visit
        /// http://www.yelp.com/developers/getting_started/api_access
        /// </summary>
        /// <returns></returns>
        public static Options Options
        {
            get
            {
                if (_options == null)
                {
                    // get all of the options out of EnvironmentSettings. You can easily just put your own keys in here without
                    // doing the env dance, if you so choose
                    _options = new Options()
                    {
                        AccessToken = "8LFG8otGRsFOLyp7a2X3xW10TNC1Qzc2",
                        AccessTokenSecret = "zSUuEMo8s_2M67K6aR9uAGuS5jk",
                        ConsumerKey = "KxtJsUMBn5ZspuMllThsjA",
                        ConsumerSecret = "3mbGugQePSd2LcfbHppnNK-N7qo"
                    };
                    if (String.IsNullOrEmpty(_options.AccessToken) ||
                    String.IsNullOrEmpty(_options.AccessTokenSecret) ||
                    String.IsNullOrEmpty(_options.ConsumerKey) ||
                    String.IsNullOrEmpty(_options.ConsumerSecret))
                    {
                        throw new InvalidOperationException("No OAuth info available. Please modify Config.cs to add your YELP API OAuth keys");
                    }
                }
                return _options;
            }
        }
    }
}