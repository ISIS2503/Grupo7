import React from 'react';
import { Redirect, Route, Router } from 'react-router-dom';
import App from './App';
import Home from './Home/Home';
import Profile from './Profile/Profile';
import Callback from './Callback/Callback';
import Auth from './Auth/Auth';
import Floors from './Floors/Floors';
import Datos from './Datos/Datos';
import history from './history';
import Users from './Users/Users';

const auth = new Auth();

const handleAuthentication = (nextState, replace) => {
  if (/access_token|id_token|error/.test(nextState.location.hash)) {
    auth.handleAuthentication();
  }
}

export const makeMainRoutes = () => {
  return (
    <Router history={history} component={App}>
        <div>
          <Route path="/" render={(props) => <App auth={auth} {...props} />} />
          <Route path="/home" render={(props) =>(
            !auth.isAuthenticated()?(
              <Home auth={auth} {...props} />
            ):(
              <Profile auth={auth} {...props} />
            
            ))} />
          <Route path="/Usuarios" render={(props) =>(
            !auth.isAuthenticated()?(
              <Home auth={auth} {...props} />
            ):(
              <Users auth={auth} {...props} />
            
            ))} />
          <Route path="/profile" render={(props) => (
            !auth.isAuthenticated() ? (
              <Redirect to="/home"/>
            ) : (
              <Profile auth={auth} {...props} />
            )
          )} />
          <Route path="/floors" render={(props) => (
            !auth.isAuthenticated() || !auth.userHasRole(['admin']) ? (
              <Redirect to="/home"/>
            ) : (
              <Floors auth={auth} {...props} />
            )
          )} />
          <Route path="/Datos" render={(props) => (
            !auth.isAuthenticated() ? (
              <Redirect to="/home"/>
            ) : (
              <Datos auth={auth} {...props} />
            )
          )} />
          <Route path="/callback" render={(props) => {
            handleAuthentication(props);
            return <Callback {...props} /> 
          }}/>        
        </div>
      </Router>
  );
}
