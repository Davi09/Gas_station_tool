import React from 'react';
import { Link } from 'react-router-dom';

class Header extends React.Component {
    render() {
        let header={
            backgroundColor:"#66ccff",
            width: "100%",
            position: "fixed",
            top: "0px",
            padding: "10px"
        };
        let left={
            float:"left",
            color: "#fff",
            fontSize:"25px"
        }
        let right = {
            float:"right"
        }
        console.log("data>>",this.props.data);
        return (
            <div style={header}>
               <div style={left}> IkWish </div>
               <div style= {right}><a href="/login">login</a></div>
            </div>
        );
    }
}



export default Header; 