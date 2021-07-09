import React, { useState, useEffect, useCallback } from 'react';
import {useHistory} from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';

import Navbar from 'react-bootstrap/Navbar';
import Form from 'react-bootstrap/Form';
import FormControl from 'react-bootstrap/FormControl';
import InputGroup from 'react-bootstrap/InputGroup';
import Button from 'react-bootstrap/Button';
import { _login } from '../services/AuthenticationService';
import { _getMembershipStatus } from '../services/BookService';
import { _buyMembership } from '../services/InvoiceService';
import { getToken, removeToken, setToken } from '../services/TokenService';

function UserNavbar(props) {
	const [user, setUser] = useState({
		email: '',
		password: '',
	});
	const [hasMembership, setHasMembership] = useState(false);

	const handleUserChange = (name) => (event) => {
		const value = event.target.value;
		setUser({ ...user, [name]: value });
	};

	const login = async (e) => {
		e.preventDefault();
		console.log('here');
		try {
			const response = await _login(user);
			setToken(response.data);
			props.setCartItems([]);
			window.location.reload(true);
		} catch (error) {
			console.error(error);
		}
	};

	useEffect(() => {
		async function getMembershipStatus() {
			const response = await _getMembershipStatus();
			setHasMembership(response.data);
			console.log(response);
		}

		getMembershipStatus();
	}, []);


	const history = useHistory();
  	const buyMembership = useCallback(() => history.push('/memberships'), [history]);


	const openInNewTab = (url) => {
		console.log('here');
		const newWindow = window.open(url, '_blank', 'noopener,noreferrer');
		if (newWindow) newWindow.opener = null;
	};

	const logout = async (e) => {
		e.preventDefault();
		removeToken();
		props.setCartItems([]);
		window.location.reload(true);
	};

	return (
		<>
			<Navbar className="bg-light justify-content-between">
				{!getToken() && (
					<Form inline>
						<InputGroup>
							<InputGroup.Prepend>
								<InputGroup.Text id="basic-addon1">Email</InputGroup.Text>
							</InputGroup.Prepend>
							<FormControl
								placeholder="Email"
								aria-label="Email"
								onChange={handleUserChange('email')}
								aria-describedby="basic-addon1"
							/>
						</InputGroup>
						<InputGroup style={{ marginLeft: 20 }}>
							<InputGroup.Prepend>
								<InputGroup.Text id="basic-addon1">Password</InputGroup.Text>
							</InputGroup.Prepend>
							<FormControl
								placeholder="*******"
								aria-label="Password"
								onChange={handleUserChange('password')}
								aria-describedby="basic-addon1"
								type="password"
							/>
						</InputGroup>
						<Button style={{ marginLeft: 20 }} onClick={login} type="submit">
							Login
						</Button>
					</Form>
				)}
				{getToken() && (
					<Form inline>
						<Button onClick={logout} type="submit">
							Log out
						</Button>
					</Form>
				)}

				<Form inline>
					{props.itemsInCart.length > 0 && (<Button style={{marginRight:20}} onClick={props.checkoutCardItems}>Checkout</Button>)}
					{getToken() && hasMembership && (
						<Button disabled >You are a member</Button>
					)}
					{getToken() && !hasMembership && (
						<Button onClick={buyMembership}>Buy membership</Button>
					)}
				</Form>

			</Navbar>
		</>
	);
}

export default UserNavbar;
