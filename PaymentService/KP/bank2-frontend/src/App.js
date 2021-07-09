import React from "react";
import { BrowserRouter as Router, Switch, Route } from "react-router-dom";
import PaymentPage from "./pages/PaymentPage";
import "bootstrap/dist/css/bootstrap.min.css";

function App() {
  return (
    <Router>
      <Switch>
        <Route path="/payment/:id" component={PaymentPage} />
      </Switch>
    </Router>
  );
}

export default App;
