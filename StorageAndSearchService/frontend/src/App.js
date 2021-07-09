import React from "react";
import { BrowserRouter as Router, Switch, Route } from "react-router-dom";
import 'bootswatch/dist/cerulean/bootstrap.min.css'; // Added this :boom:
import MainPage from "./pages/MainPage";
import NotFoundPage from "./pages/NotFoundPage";
import FindReviewersPage from "./pages/FindReviewersPage";

function App() {
  return (
    <>
      <Router>
        <Switch>
          <Route path="/" exact component={() => <MainPage />} />
          <Route path="/reviewers" exact component={() => <FindReviewersPage />} />
          <Route path="*" component={() => <NotFoundPage />} />
        </Switch>
      </Router>
    </>
  );
}

export default App;
