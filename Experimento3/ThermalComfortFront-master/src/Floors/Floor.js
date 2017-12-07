import React, { Component } from 'react';
import { Button, Glyphicon, Modal, FormControl, FormGroup, ControlLabel } from 'react-bootstrap';
import { API_URL } from './../constants';
import axios from 'axios';

class Floor extends Component {
	componentWillMount() {
		this.setState({
			showModal: false,
			name: '',
			code: ''
		});
	}
	handleNameChange(event) {
		this.setState({ name: event.target.value });
	}
	handleCodeChange(event) {
		this.setState({ code: event.target.value });
	}
	deleteFloor() {
		const { getIdToken } = this.props.auth;
		const headers = { Authorization: `Bearer ${getIdToken()}` };
		axios.delete(`${API_URL}/floors/${this.props.id}`, { credentials: true, headers: headers })
			.then(response => {
				console.log("Deleted successfully");
				this.props.getFloors();
			});
	}
	updateFloor() {
		const { getIdToken } = this.props.auth;
		const floor = { id: this.props.id, name: this.state.name, code: this.state.code };
		const headers = { Authorization: `Bearer ${getIdToken()}` };
		axios.put(`${API_URL}/floors`, floor, { credentials: true, headers: headers })
			.then(response => {
				console.log("Updated successfully");
				this.close();
				this.props.getFloors();
			});
	}
	close() {
		this.setState({ showModal: false });
	}

	open() {
		this.setState({ showModal: true });
	}
	render() {
		return (
			<tr>
				<td>{this.props.number}</td>
				<td>{this.props.id}</td>
				<td>{this.props.type}</td>
				<td>{this.props.fecha}</td>
				<td>{this.props.dataValue}</td>
				<td>{this.props.unit}</td>
				<td>{this.props.descripcion}</td>
				<td>{this.props.ubicacion}</td>
			</tr>

		);
	}
}
export default Floor;