import React from 'react';
import { BrowserRouter as Router, Switch, Route } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';

import StorePage from './pages/StorePage';
import OrderSuccessPage from './pages/OrderSuccessPage';
import OrderFailurePage from './pages/OrderFailurePage';

import { _login } from './services/AuthenticationService';
import MembershipPage from './pages/MembershipSelect';

function App() {
	return (
		<>
			<>
				<Router>
					<Switch>
						<Route path="/order/success" component={OrderSuccessPage} />
					</Switch>
					<Switch>
						<Route path="/order/failure" component={OrderFailurePage} />
					</Switch>
					<Switch>
						<Route path="/memberships" component={MembershipPage} />
					</Switch>
					<Switch>
						<Route path="/" exact component={() => <StorePage />} />
					</Switch>
				</Router>
			</>
		</>
	);
}

export default App;
