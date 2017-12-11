import React, { Component } from 'react';
import { Table, Button, FormGroup, ControlLabel, FormControl, Glyphicon } from 'react-bootstrap';
import { API_URL } from './../constants';
import Floor from './Floor';
import axios from 'axios';

class Floors extends Component {
  componentWillMount() {
    this.setState({
      alarms: [],
      message: '',
      name: '',
      code: ''
    });
  }
  
  componentDidMount() {
    this.getAlarms();
  }
  handleNameChange(event) {
    this.setState({ name: event.target.value });
  }
  handleCodeChange(event) {
    this.setState({ code: event.target.value });
  }
  /*addFloor(event) {
    event.preventDefault();
    const { getIdToken } = this.props.auth;
    const headers = { Authorization: `Bearer ${getIdToken()}`};
    const floor = { name: this.state.name, code: this.state.code };
    axios.post(`${API_URL}/alarms`, floor, { credentials: true, headers: headers })
    .then(response => this.getAlarms())
    .catch(error => this.setState({ message: error.message }));
  }*/
  getAlarms() {
    const { getIdToken } = this.props.auth;
    const headers = { Authorization: `Bearer ${getIdToken()}` };
    axios.get(`${API_URL}/alarms`, { credentials: true, headers: headers })
      .then(response => this.setState({ alarms: response.data }))
      .catch(error => this.setState({ message: error.message }));
      console.log(this.state.alarms);
  }
  render() {
    let FloorStyles={
      color: '#000000'
    }
    const { isAuthenticated } = this.props.auth;
    return (
      <div className="container">
        <h1 style={FloorStyles}>Alarmas</h1>
        {/*<h2>Add a floor</h2>*/}
        {/*<form onSubmit={(event) => this.addFloor(event)}>
        <FormGroup controlId="formInlineName">
          <ControlLabel>Name</ControlLabel>
          {' '}
          <FormControl type="text" placeholder="Name" onChange={(event) => this.handleNameChange(event)} />
        </FormGroup>
        {' '}
        <FormGroup controlId="formInlineCode">
          <ControlLabel>Code</ControlLabel>
          {' '}
          <FormControl type="text" placeholder="Code" onChange={(event) => this.handleCodeChange(event)} />
        </FormGroup>
        {' '}
        <Button bsStyle="success" type="submit">
          <Glyphicon glyph="plus" /> Add
        </Button>
    </form>*/}
        <br />
        <Table bordered condensed hover className="center" >
          <thead>
            <tr>
              <th>#</th>
              <th>ID</th>
              <th>Tipo</th>
              <th>Fecha</th>
              <th>Valor</th>
              <th>Unidad</th>
              <th>Descripcion</th>
              <th>Ubicacion</th>
            </tr>
          </thead>
          <tbody >
            {this.state.alarms.map((alarm, index) => {
              return (
                <Floor 
                  key={index}
                  number={index + 1}
                  id={alarm.id}
                  type={alarm.type}
                  fecha={alarm.alarmtime}
                  dataValue={alarm.dataValue}
                  unit={alarm.unit}
                  descripcion={alarm.descripcion}
                  ubicacion={alarm.ubicacion}
                  auth={this.props.auth}
                  getAlarms={() => this.getAlarms()}
                />
              );
            })}
          </tbody>
        </Table>
        <h2>{this.state.message}</h2>
      </div>
    );
  }
}

export default Floors;
