import React, { useState } from 'react';

const Alert = (props) => {
	if (props.showAlert) {
		return (
			<Alert variant="danger" onClose={() => props.setShowAlert(false)} dismissible>
				<Alert.Heading>{props.heading}</Alert.Heading>
				<p>{props.message}</p>
			</Alert>
		);
	}
};

export default Alert;
