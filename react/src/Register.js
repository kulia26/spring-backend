import React from 'react';
import { Form, Field } from 'react-final-form'
import validator from 'validator';
const axios = require('axios');



const validateRecord = ( values) => {
    var errors = {};
    if (!values.phone ) {
      errors.phone = "Пустий телефон";
    }else{
      if (!validator.isMobilePhone(values.phone)){
        errors.phone = 'Неправильний телефон' 
      }
    }
    if (!values.image) {
      //errors.logo = "Завантажте фото";
    }
    if (!values.name || !validator.isAlphanumeric(values.name+'') || values.length < 5) {
      errors.name = "Ім'я може містити тільки A-z0-9";
    }
    if (!values.password || !validator.isAlphanumeric(values.password+'')) {
      errors.password = "Пароль може містити тільки A-z0-9";
    }
    return errors;
  }
  


class Register extends React.Component {

  constructor(props) {
    super(props);
    this.state = {message: 'Введіть свої дані нижче', image : ''};
    this.onSubmit = this.onSubmit.bind(this);
    this.fileChangedHandler = this.fileChangedHandler.bind(this);
  }
  
  fileChangedHandler(event) {
    let file = event.target.files[0];
    this.setState({image: file});
  }


   onSubmit(values) {
    const url = 'http://localhost:8000/register';
    const formData = new FormData();
    
    formData.append('image', this.state.image);
    formData.append('name', values.name);
    formData.append('phone', values.phone);
    formData.append('password', values.password);
    
    const config = {
        headers: {
            'content-type': 'multipart/form-data'
        }
    }
    axios.post(url, formData, config)
    //axios.post('http://localhost:8000/register', values)
    .then(res => {
      console.log({res});
      this.setState({ message : 'Реєстрація успішна, '+res.data.name+'\n Натисніть кнопку "увійти"'})
      
    })
    .catch(err => {
      console.log({err});
      this.setState({ message :  err.response.data.message})
    
    });
  };
  render(){
    return(
    <>
    <div className="flex">
      <h1>Реєстрація </h1>
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
                    <Field name={`name`}>
                      {({ input, meta }) => (
                        <>
                          <label>Ім'я</label>
                          <input {...input} type="text" placeholder="Як вас звати ?" />
                          <p className="error">{meta.error}</p>
                        </>
                      )}
                    </Field>
                    <Field name={`phone`}>
                      {({ input, meta }) => (
                        <>
                          <label>Телефон</label>
                          <input {...input} type="text" placeholder="Ваш телефон" />
                          <p className="error">{meta.error}</p>
                        </>
                      )}
                    </Field>
                    <Field name={`password`}>
                      {({ input, meta }) => (
                        <>
                          <label>Пароль</label>
                          <input {...input} type="password" placeholder="Оберіть пароль" />
                          <p className="error">{meta.error}</p>
                        </>
                      )}
                    </Field>
                    
                    <Field name={`image`}>
                      {({ input, meta }) => (
                        <>
                            <label>Фотографія</label>
                            <input {...input} onChange={this.fileChangedHandler} type="file" accept="image/jpeg"/ >
                            <p className="error">{meta.error}</p>
                        </>
                      )}
                    </Field>
                    <div className="buttons">
                      <button type="submit" disabled={props.submitting || props.pristine}>
                        Зареєструватися
                      </button>
                    </div>
                  </form>
                </>
                )
            }}
          />
      </div>
    </section>
    </>
   )
}};

export default Register;
