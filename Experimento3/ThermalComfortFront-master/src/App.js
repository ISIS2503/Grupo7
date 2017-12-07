import React, { Component } from 'react';
import { Navbar, Button, Row, Col } from 'react-bootstrap';
import './App.css';
import { slide as Menu } from 'react-burger-menu';

class App extends Component {
  goTo(route) {
    this.props.history.replace(`/${route}`)
  }

  login() {
    this.props.auth.login();
  }

  logout() {
    this.props.auth.logout();
  }

  showSettings(event) {
    event.preventDefault();
  }

  render() {

    let systems = [
      <a key="0" href=""><i className="fa fa-star" /><span>Variables en tiempo real</span></a>,
      <a key="1" href=""><i className="fa fa-fw fa-star-o" /><span>Alertas</span></a>
    ];

    let supervisors = [
      <a key="2" onClick={this.goTo.bind(this, 'profile')}><i className="fa fa-fw fa-star-o" /><span>Perfil</span></a>,
      <a key="0"onClick={this.goTo.bind(this, 'Datos')}><i className="fa fa-fw fa-star-o" /><span>Variables en tiempo real</span></a>,
      <a key="1" href=""><i className="fa fa-fw fa-star-o" /><span>Alertas</span></a>,
      <a key="2" href=""><i className="fa fa-fw fa-star-o" /><span>Usuarios registrados</span></a>
      
    ];

    let crossStyle = {
      position: 'absolute',
      width: 3,
      height: 14,
      transform: 'rotate(45deg)' /*: 'rotate(-45deg)'*/
    };

    let buttonWrapperStyle = {
      position: 'absolute',
      width: 24,
      height: 24,
      right: 8,
      top: 8
    };

    let buttonStyle = {
      position: 'absolute',
      left: 0,
      top: 0,
      width: '100%',
      height: '100%',
      margin: 0,
      padding: 0,
      border: 'none',
      textIndent: -9999,
      background: 'transparent',
      outline: 'none',
      cursor: 'pointer'
    };

    let crossIcon = (
      <span style={{ position: 'absolute', top: '6px', right: '14px' }}>
        <span
          key="0"
          className='bm-cross'
          style={crossStyle}
        />
        ))}
      </span>
    )

    const { isAuthenticated, userHasRole } = this.props.auth;

    return (
      <div>
        <div>
          <div>
            {
              isAuthenticated() && userHasRole(['admin']) && (
                <Menu >
                  {
                    (supervisors).map((supervisor) => {
                      {
                        <div
                          className='bm-cross-button'
                          style={buttonWrapperStyle}
                        >
                          {crossIcon}
                          <button style={buttonStyle}>
                            Close Menu
                          </button>
                        </div>
                      }
                      return (
                        supervisor
                      )
                    })
                  }
                </Menu>
              )
            }
          </div>

          <Navbar fluid>
            <div className="navbarLogin">
              <Navbar.Header>
                <Navbar.Brand>
                  <a href="/#">Mina Samacá</a>
                </Navbar.Brand>
              </Navbar.Header>
              {
                !isAuthenticated() && (
                  <Button bsStyle="primary" className="btn-margin pull-right" onClick={this.login.bind(this)}>
                    Log In
                </Button>
                )
              }
              {
                isAuthenticated() && (
                  <Button bsStyle="primary" className="btn-margin" onClick={this.goTo.bind(this, 'floors')}>
                    Profile
                </Button>
                )
              }
              {

                isAuthenticated() && (
                  <Button bsStyle="primary" className="btn-margin pull-right" onClick={this.logout.bind(this)}>
                    Log Out
                </Button>
                )
              }
            </div>
          </Navbar>
        </div>
      </div>
    );
  }
}

export default App;
