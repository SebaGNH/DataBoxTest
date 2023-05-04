import React from 'react';
import { Navigate, Route, Routes } from 'react-router-dom';
import Navbar from '../components/layout/Navbar/Navbar';
import CurrentPricesRoutes from './CurrentPrices.routes';
import BulkLoadRoutes from './BulkLoad.routes';
import BulkLoadRoutesValidation from './BulkLoadValidation.routes';

const AppRoutes = () => {
    return (
        <>
            <Navbar />
            <div className="layout__content" id="rmt_layoutContent">
                <Routes>
                    <Route key="redirect" path="*" element={<Navigate to="/current-prices" replace />} />
                    {CurrentPricesRoutes}
                    {BulkLoadRoutes}
                    {BulkLoadRoutesValidation}
                </Routes>
            </div>
        </>
    );
};

export default AppRoutes;
