import React, { useState } from 'react';
import Toolbar from '../../layout/Toolbar/Toolbar';
import './bulk-load-validation.scss';
import FilterBulkLoadValidation from './filter-bulk-load-validation/filter-bulk-load-validation';
import { useQuery } from 'react-query';
import { bulkLoadService } from '../../../services/bulk-load.service';
import { useParams } from 'react-router';

const BulkLoadValidation = () => {
    const [filters, setFilters] = useState<any>({});
    const { csvId } = useParams();
    const [isBack, setIsBack] = useState<boolean>(true);
    const [page, setPage] = useState(0);
    const pageSize = 10;
    const [totalElements, setTotalElements] = useState<number>(0);

    const { data: fileData, isLoading: isLoadingArticles } = useQuery(
        ['filesCsv', page, pageSize, filters],
        async () => {
            if (isBack && csvId) {
                filters.idLote = csvId;
                setIsBack(false);
            }
            if (filters.idLote == null) filters.idLote = '';
            const data = await bulkLoadService.getFilterLotes(page, pageSize, filters);
            setTotalElements(data?.totalElements);
            return data;
        },
        { keepPreviousData: true, refetchOnWindowFocus: false }
    );

    const handlePage = (page: number) => {
        setPage(page);
    };

    const handleFilters = (filters: any) => {
        setFilters(filters);
        setPage(0);
    };

    return (
        <>
            <div className="layout__toolbar">
                <Toolbar />
            </div>
            <div className="container">
                <FilterBulkLoadValidation
                    isLoading={isLoadingArticles}
                    handleFilters={handleFilters}
                    fileData={fileData}
                    page={page}
                    pageSize={pageSize}
                    totalElements={totalElements}
                    handlePage={handlePage}
                    csvIdParam={csvId}
                />
            </div>
        </>
    );
};

export default BulkLoadValidation;
