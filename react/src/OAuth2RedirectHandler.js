import React, { Component } from 'react';
import { Redirect } from 'react-router-dom';
import axios from 'axios';

class OAuth2RedirectHandler extends Component {
    getUrlParameter(name) {
        name = name.replace(/[\[]/, '\\[').replace(/[\]]/, '\\]');
        var regex = new RegExp('[\\?&]' + name + '=([^&#]*)');

        var results = regex.exec(this.props.location.search);
        return results === null ? '' : decodeURIComponent(results[1].replace(/\+/g, ' '));
    };

    render() {        
        let token = this.getUrlParameter('token');
        const error = this.getUrlParameter('error');
        
        if(token) {

            let ourToken = {};
            ourToken.accessToken = token;
            ourToken.tokenType = 'Bearer';
            sessionStorage.setItem('token',JSON.stringify(ourToken));

            return <Redirect to={{
                pathname: "/login",
                state: { 
                    from: this.props.location,
                }
            }}/>; 
        } else {
            return <Redirect to={{
                pathname: "/login",
                state: { 
                    from: this.props.location,
                    message: error 
                }
            }}/>; 
        }
    }
}

export default OAuth2RedirectHandler;