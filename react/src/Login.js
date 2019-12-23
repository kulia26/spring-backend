import React from 'react';
import { Form, Field } from 'react-final-form'
import validator from 'validator';
import { Redirect } from "react-router-dom";

import { Button } from 'semantic-ui-react'
const axios = require('axios');


const validateRecord = (values) => {
    var errors = {};
    if (!values.phone ) {
      errors.phone = "Пустий телефон";
    }else{
      if (!validator.isMobilePhone(values.phone)){
        errors.phone = 'Неправильний телефон' 
      }
    }
    if (!values.password || !validator.isAlphanumeric(values.password+'')) {
      errors.password = "Пароль може містити тільки A-z0-9";
    }
    return errors;
}
class SocialLogin extends React.Component {
  render() {
      return (
          <div className="social-login">
              <a className="btn btn-block social-btn github" href={'http://localhost:8000/oauth2/authorize/github?redirect_uri=http://localhost:3000/oauth2/redirect'}>
                  {/* <img src={githubLogo} alt="Github" /> */}
                  <Button primary>Login with github.com</Button>
              </a> 
          </div>
      );
  }
}
class Login extends React.Component {

  constructor(props) {
    super(props);
    this.state = {
      user: sessionStorage.getItem('user'),
      message: 'Введіть свої дані нижче', 
     };
    this.onSubmit = this.onSubmit.bind(this);
    this.componentDidMount = this.componentDidMount.bind(this);
  }

  
 componentDidMount() {
   if(!this.state.user){
    let token = JSON.parse(sessionStorage.getItem('token'));
      if(token) {
        let config = {
          headers: {
              "Authorization" : token.tokenType+' '+ token.accessToken,
          }
        }
          axios.get('http://localhost:8000/user/me', config)
          .then((res) =>{
            sessionStorage.setItem('user', JSON.stringify(res.data));
            this.props.onLogIn();
            this.setState({user: JSON.stringify(res.data) });
          })
      }
   }
}

  onSubmit(values) {
    console.log(JSON.stringify(values))
    let state = {}
    axios.post('http://localhost:8000/login', values)
    .then((res) =>{
      state.message = "Вітаємо!";
      state.token = res.data;
      sessionStorage.setItem('token', JSON.stringify(res.data));
      if(state.token.accessToken){
        axios.get('http://localhost:8000/users/' + values.phone +'/')
        .then((res) =>{
          state.user = res.data;
          sessionStorage.setItem('user', JSON.stringify(res.data));
          this.props.onLogIn();
          this.setState(state);
        })   
      }
    })
    .catch((err) =>{
      state.message = err.response.data.message;
      this.setState(state);
    });
    this.setState(state);
    
  };
  render(){
    return(
    <>
    {this.state.user ? <Redirect to="/orders" /> : <></>}
    <div className="flex">
      <h1>Вхід </h1>
    </div>
    <div className="flex">
      <h2>{this.state.message}</h2>
    </div>
      <section className="flex col5">
          <div>
            <Form
            onSubmit={this.onSubmit}
            validate={validateRecord}
            validateOnBlur={false}
            onChange={validateRecord}
            render={(props) => {
              return (
                <>
                  <form onSubmit={props.handleSubmit} className="flex column">
                    <Field name={`phone`}>
                      {({ input, meta }) => (
                        <>
                          <label>Телефон</label>
                          <input {...input} type="text" placeholder="Ваша телефон" />
                          <p className="error">{meta.error}</p>
                        </>
                      )}
                    </Field>
                    <Field name={`password`}>
                      {({ input, meta }) => (
                        <>
                          <label>Пароль</label>
                          <input {...input} type="password" placeholder="Введіть пароль" />
                          <p className="error">{meta.error}</p>
                        </>
                      )}
                    </Field>
                    <div className="buttons">
                      <button type="submit" disabled={props.submitting || props.pristine}>
                        Увійти
                      </button>
                    </div>
                  </form>
                  <SocialLogin />
                </>
                )
            }}
          />
      </div>
    </section>
    </>
   )
}};

export default Login;
