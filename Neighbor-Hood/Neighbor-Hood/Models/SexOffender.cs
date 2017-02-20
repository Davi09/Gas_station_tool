using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Neighbor_Hood.Models
{
    public class SexOffender
    {
        public int Id { get; set; }
        public string Zip { get; set; }
        public string Name { get; set; }
        public string Address { get; set; }
        public string lat { get; set; }
        public string lng { get; set; }
    }
}