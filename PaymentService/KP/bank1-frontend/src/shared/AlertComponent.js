import React from 'react';

import Alert from 'react-bootstrap/Alert';

const AlertComponent = (props) => {
	if (props.alert) {
		return (
			<Alert variant="danger" onClose={() => props.setAlert(null)} dismissible>
				<Alert.Heading>{props.alert.heading}</Alert.Heading>
				<p>
					{props.alert.violations.map((m) => (
						<li>{m.message}</li>
					))}
				</p>
			</Alert>
		);
	}
};

export default AlertComponent;
