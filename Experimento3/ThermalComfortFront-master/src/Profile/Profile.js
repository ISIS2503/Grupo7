import React, { Component } from 'react';
import { Panel, ControlLabel, Glyphicon } from 'react-bootstrap';
import './Profile.css';

class Profile extends Component {

  componentWillMount() {
    this.setState({ profile: {} });
    const { userProfile, getProfile } = this.props.auth;

    if (!userProfile) {
      getProfile((err, profile) => {
        this.setState({ profile });
      });
    } else {
      this.setState({ profile: userProfile });
      window.profile = userProfile;
    }
  }
  render() {
    const { isAuthenticated, login } = this.props.auth;
    const { profile } = this.state;
    console.log(profile);
    return (
      <div>
        <div className="container" >
          <div className="row login_box" style={{ float: 'center' }}>
            <div className="col-md-12" style={{ align: 'center' }}>
              <div className="outter" style={{ marginLeft: '180px', marginTop: '40px' }}><img src={profile.picture} className="image-circle" /></div>

              <div className="control" style={{ align: 'center' }}>
                <h1>¡Hola! {profile.nickname}</h1>
                <div className="label"> <Glyphicon glyph="envelope" /> Correo: </div>
                <p> {profile.name}</p>
                <p>Última modificación: {profile.updated_at}</p>
              </div>
            </div>

          </div>
        </div>
      </div>

    );
  }
}

export default Profile;
