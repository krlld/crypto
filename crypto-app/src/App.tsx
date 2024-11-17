import React from 'react';
import { ConfigProvider } from 'antd';
import { BrowserRouter as Router, Route, Routes, useLocation } from 'react-router-dom';
import LoginPage from './pages/LoginPage';
import RegisterPage from './pages/RegisterPage';
import Navbar from './components/Navbar';
import ReportsPage from './pages/ReportsPage';
import ReportCreationPage from './pages/ReportCreationPage';
import CurrenciesPage from './pages/CurrenciesPage';
import CryptoSubscriptionPage from './pages/CryptoSubscriptionPage';
import ProfilePage from './pages/ProfilePage';
import RolesPage from './pages/RolesPage';
import RoleAssignmentPage from './pages/RoleAssignmentPage';

const App: React.FC = () => {
	const location = useLocation();

	const showNavbar = location.pathname !== '/login' && location.pathname !== '/register';

	return (
		<div>
			{showNavbar && <Navbar />}
			<Routes>
				<Route path="/login" element={<LoginPage />} />
				<Route path="/register" element={<RegisterPage />} />
				<Route path="/" element={<ReportsPage />} />
				<Route path="/create-report" element={<ReportCreationPage />} />
				<Route path="/currencies" element={<CurrenciesPage />} />
				<Route path="/crypto-subscriptions" element={<CryptoSubscriptionPage />} />
				<Route path="/profile" element={<ProfilePage />} />
				<Route path="/roles" element={<RolesPage />} />
				<Route path="/reassign-roles" element={<RoleAssignmentPage />} />
			</Routes>
		</div>
	);
};

const MainApp: React.FC = () => (
	<ConfigProvider theme={{ token: { colorPrimary: '#00b96b' } }}>
		<Router>
			<App />
		</Router>
	</ConfigProvider>
);

export default MainApp;
