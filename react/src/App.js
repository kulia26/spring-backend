import React from 'react';
import { BrowserRouter as Router, Route, Switch } from "react-router-dom";
import Header from './Header';
import Nav from './Nav';
import Footer from './Footer';
import Sidebar from './Sidebar';
import Cards from './Cards';
import About from './About';
import Orders from './Orders';
import NoMatch from './NoMatch';
import Register from './Register';
import ServerError from './ServerError';
import Login from './Login';
import AddItem from './AddItem';
import AddRespond from './AddRespond';
import EditItem from './EditItem';
import Item from './Item';
import categories from './categories';
import OAuth2RedirectHandler from './OAuth2RedirectHandler.js';

class App extends React.Component{
  constructor(props) {
    super(props);
    let user = sessionStorage.getItem('user');
    user = user ? JSON.parse(user) : {
      roles:[]
    };
    this.state = {
       login: user.name ? true: false,
       phone: user ? user.phone : '', 
       isAdmin:  user.roles.length > 1 ? true : false,
       editing: false,
       user: user.roles.length > 0 ? user : undefined,
    };
    this.logout  = this.logout.bind(this);
    this.login = this.login.bind(this);
    this.disable = this.disable.bind(this);
    this.edit = this.edit.bind(this);
  }

  logout(){
    sessionStorage.removeItem('user');
    sessionStorage.removeItem('token');
    this.setState({
      login: false,
      isAdmin:  false,
      user: null,
    });

  }

  disable(){
    sessionStorage.removeItem('editing');
    this.setState({
      editing: false,
    });
  }

  login(){
    let user = sessionStorage.getItem('user');
    user = user ? JSON.parse(user) : {
      roles:[]
    };
      this.setState({
        login: user.name ? true : false,
        phone: user.phone,
        isAdmin: user.roles.length > 1 ? true : false,
        user: user,
        });
  }

  edit(){
    this.setState({
      editing: true,
      });
  }


  renderRouterCategories(){
    let render = [];
    for (const key in categories) {
      render.push( <Route key ={key} exact path={/category/+key} render={() => <Cards onEdit={this.edit} isAdmin={this.state.isAdmin} title={categories[key]} category={key} />}/>);
    }
    return render;
  }

  render(){
    return (
      <Router>
      <div className="App">
        <Header login={this.state.login} imageUrl={this.state.user ? this.state.user.imageUrl : null} phone={this.state.phone} onLogOut={this.logout} />
        <Nav />
        <div className="flex  content">
              <Sidebar />
              <div className="col5 main">
                <Switch >
                  <Route exact path="/" render={() => <Cards user={this.state.user} onEdit={this.edit} isAdmin={this.state.isAdmin} title="  Розпродаж" />}/>
                  <Route exact path="/index" render={() => <Cards user={this.state.user} onEdit={this.edit} isAdmin={this.state.isAdmin} title="  Розпродаж" />}/>
                  <Route path="/search/:string" render={() => <Cards user={this.state.user} isSearch={true} onEdit={this.edit} isAdmin={this.state.isAdmin} title="  Розпродаж" />}/>
                  {this.renderRouterCategories()}
                  <Route path="/about" component={About} />
                  <Route path="/orders" render={() => <Orders user={this.state.user} isAdmin={this.state.isAdmin}/>} />
                  <Route path="/register" component={Register} />
                  <Route path="/additem" component={AddItem} />
                  <Route path="/edititem" render={() => <EditItem onDisable={this.disable}/>}/>
                  <Route path="/products/:id" render={(props) => <Item login={this.state.login} {...props}/>}/>
                  <Route path="/addRespond/:id" render={(props) => <AddRespond  {...props} />}/>
                  <Route exact path="/login" render={() => <Login onLogIn={this.login} />}/>
                  <Route path="/oauth2/redirect" component={OAuth2RedirectHandler}></Route> 
                  <Route component={NoMatch} />
                </Switch>
              </div>
        </div>
        <Footer />
      </div>
      </Router>
    );
  }
}

export default App;
