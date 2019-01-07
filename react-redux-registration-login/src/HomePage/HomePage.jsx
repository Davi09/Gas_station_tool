import React from 'react';
import { Link } from 'react-router-dom';
import { connect } from 'react-redux';

import { userActions } from '../_actions';
import { FilePond } from 'react-filepond';
import Table from '../_components/Tables/Tables';


class HomePage extends React.Component {
    constructor(props){
        super();
        this.state = {
            showTable:false
        }
        this.processFile = this.processFile.bind(this);
    }
    componentDidMount() {
        this.props.dispatch(userActions.getAll());
        
    }

    handleDeleteUser(id) {
        return (e) => this.props.dispatch(userActions.delete(id));
    }
    
    processFile(){
            this.setState ({
                showTable:true
            });

    }
    render() {
        const { user, users } = this.props;
        return (
            <div className="col-lg-12 ">
                <h3>Please upload your file to process</h3>
                <input type="file" id="myFile"/>
                <button onClick={this.processFile}>Process File</button>
                <div>
                    {this.state.showTable && <Table/>}
                    </div>
            </div>
        );
    }
}

function mapStateToProps(state) {
    const { users, authentication } = state;
    const { user } = authentication;
    return {
        user,
        users
    };
}

const connectedHomePage = connect(mapStateToProps)(HomePage);
export { connectedHomePage as HomePage };