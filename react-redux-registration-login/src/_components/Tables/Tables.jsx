import React from "react";
import { render } from "react-dom";

// Import React Table
import ReactTable from "react-table";
import "react-table/react-table.css";

class Table extends React.Component {
  constructor() {
    super();
    this.state = {
      data: [
        {
          deptname:"alternative snacks",
          grosssale:"$38.40",
          itemcount:11,
          refundcount:0,
          netsales:"$38.40",
          slaespercent:"3.88%"
        },
        {
          deptname:"automotive products",
          grosssale:"$38.40",
          itemcount:11,
          refundcount:0,
          netsales:"$38.40",
          slaespercent:"3.88%"
        },
        {
          deptname:"automotive services",
          grosssale:"$38.40",
          itemcount:11,
          refundcount:0,
          netsales:"$38.40",
          slaespercent:"3.88%"
        },
        {
          deptname:"beer",
          grosssale:"$38.40",
          itemcount:11,
          refundcount:0,
          netsales:"$38.40",
          slaespercent:"3.88%"
        },
        {
          deptname:"bottle deposits",
          grosssale:"$38.40",
          itemcount:11,
          refundcount:0,
          netsales:"$38.40",
          slaespercent:"3.88%"
        },
        {
          deptname:"edible grocer",
          grosssale:"$38.40",
          itemcount:11,
          refundcount:0,
          netsales:"$38.40",
          slaespercent:"3.88%"
        },
        {
          deptname:"cnady",
          grosssale:"$38.40",
          itemcount:11,
          refundcount:0,
          netsales:"$38.40",
          slaespercent:"3.88%"
        },
        {
          deptname:"cig",
          grosssale:"$38.40",
          itemcount:11,
          refundcount:0,
          netsales:"$38.40",
          slaespercent:"3.88%"
        },
        {
          deptname:"ecig",
          grosssale:"$38.40",
          itemcount:11,
          refundcount:0,
          netsales:"$38.40",
          slaespercent:"3.88%"
        },
        {
          deptname:"General merchandise",
          grosssale:"$38.40",
          itemcount:11,
          refundcount:0,
          netsales:"$38.40",
          slaespercent:"3.88%"
        },
        {
          deptname:"health  & beuty",
          grosssale:"$38.40",
          itemcount:11,
          refundcount:0,
          netsales:"$38.40",
          slaespercent:"3.88%"
        },
        {
          deptname:"jull",
          grosssale:"$38.40",
          itemcount:11,
          refundcount:0,
          netsales:"$38.40",
          slaespercent:"3.88%"
        },
        {
          deptname:"milk and dairy",
          grosssale:"$38.40",
          itemcount:11,
          refundcount:0,
          netsales:"$38.40",
          slaespercent:"3.88%"
        },
        {
          deptname:"package sweet snack",
          grosssale:"$38.40",
          itemcount:11,
          refundcount:0,
          netsales:"$38.40",
          slaespercent:"3.88%"
        },
        {
          deptname:"publications",
          grosssale:"$38.40",
          itemcount:11,
          refundcount:0,
          netsales:"$38.40",
          slaespercent:"3.88%"
        },
        {
          deptname:"salty snacks",
          grosssale:"$38.40",
          itemcount:11,
          refundcount:0,
          netsales:"$38.40",
          slaespercent:"3.88%"
        },
        {
          deptname:"soda",
          grosssale:"$38.40",
          itemcount:11,
          refundcount:0,
          netsales:"$38.40",
          slaespercent:"3.88%"
        },
        {
          deptname:"tea",
          grosssale:"$38.40",
          itemcount:11,
          refundcount:0,
          netsales:"$38.40",
          slaespercent:"3.88%"
        },
        {
          deptname:"tobacco",
          grosssale:"$38.40",
          itemcount:11,
          refundcount:0,
          netsales:"$38.40",
          slaespercent:"3.88%"
        },
        {
          deptname:"water",
          grosssale:"$38.40",
          itemcount:11,
          refundcount:0,
          netsales:"$38.40",
          slaespercent:"3.88%"
        },
        {
          deptname:"alternative snacks",
          grosssale:"$38.40",
          itemcount:11,
          refundcount:0,
          netsales:"$38.40",
          slaespercent:"3.88%"
        },
        {
          deptname:"alternative snacks",
          grosssale:"$38.40",
          itemcount:11,
          refundcount:0,
          netsales:"$38.40",
          slaespercent:"3.88%"
        },
        {
          deptname:"alternative snacks",
          grosssale:"$38.40",
          itemcount:11,
          refundcount:0,
          netsales:"$38.40",
          slaespercent:"3.88%"
        },
        {
          deptname:"alternative snacks",
          grosssale:"$38.40",
          itemcount:11,
          refundcount:0,
          netsales:"$38.40",
          slaespercent:"3.88%"
        },
        {
          deptname:"alternative snacks",
          grosssale:"$38.40",
          itemcount:11,
          refundcount:0,
          netsales:"$38.40",
          slaespercent:"3.88%"
        },
        {
          deptname:"alternative snacks",
          grosssale:"$38.40",
          itemcount:11,
          refundcount:0,
          netsales:"$38.40",
          slaespercent:"3.88%"
        },
        {
          deptname:"alternative snacks",
          grosssale:"$38.40",
          itemcount:11,
          refundcount:0,
          netsales:"$38.40",
          slaespercent:"3.88%"
        },
        {
          deptname:"alternative snacks",
          grosssale:"$38.40",
          itemcount:11,
          refundcount:0,
          netsales:"$38.40",
          slaespercent:"3.88%"
        },
        {
          deptname:"alternative snacks",
          grosssale:"$38.40",
          itemcount:11,
          refundcount:0,
          netsales:"$38.40",
          slaespercent:"3.88%"
        },
        {
          deptname:"alternative snacks",
          grosssale:"$38.40",
          itemcount:11,
          refundcount:0,
          netsales:"$38.40",
          slaespercent:"3.88%"
        },
        {
          deptname:"alternative snacks",
          grosssale:"$38.40",
          itemcount:11,
          refundcount:0,
          netsales:"$38.40",
          slaespercent:"3.88%"
        }
      ]
    };
  }
  render() {
    const { data } = this.state;
    return (
      <div>
        <ReactTable
          data={data}
          columns={[
            {
              Header: "Name",
              columns: [
                {
                  Header: "Dept. Name",
                  accessor: "deptname"
                },
                {
                  Header: "Gross sale",
                  id: "grosssale",
                  accessor: "grosssale"
                }
              ]
            },
            {
              Header: "Info",
              columns: [
                {
                  Header: "Item count",
                  accessor: "itemcount"
                },
                {
                  Header: "Refund count",
                  accessor: "refundcount"
                }
              ]
            },
            {
              Header: 'Stats',
              columns: [
                {
                  Header: "Net Sales",
                  accessor: "netsales"
                },
                {
                  Header: "% of sales",
                  accessor: "slaespercent"
                }
              ]
            }
          ]}
          defaultPageSize={10}
          className="-striped -highlight"
        />
        <br />
      </div>
    );
  }
}

export default Table;