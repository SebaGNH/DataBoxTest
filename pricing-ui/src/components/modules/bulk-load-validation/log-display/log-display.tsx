import React, { useState, useEffect } from 'react';
import Toolbar from '../../../layout/Toolbar/Toolbar';
import { Box, Typography, Button } from '@mui/material';
import { useTranslation } from 'react-i18next';
import { useNavigate, useParams } from 'react-router-dom';
import { ArrowBack } from '@mui/icons-material';
import { useQuery } from 'react-query';
import './log-display.scss';
import FilterLogDisplay from './filter-log-display';
import TableLogDisplay from './table-log-display/table-log-display';
import { bulkLoadService } from '../../../../services/bulk-load.service';

const LogDisplay = () => {
    const [filters, setFilters] = useState<any>({});
    const [articles, setArticles] = useState<any[]>([]);
    const [totalElements, setTotalElements] = useState<number>(0);
    const [page, setPage] = useState(0);
    const pageSize = 10;
    const { t } = useTranslation();
    const navigate = useNavigate();
    const [isPartialCheck, setPartialCheck] = useState(false);
    const { csvId } = useParams();

    const {
        data: articlesDataLD,
        isLoading: isLoadingArticles,
        isFetching
        // refetch
    } = useQuery(
        ['articles', page, pageSize, filters],
        async () => {
            if (csvId) {
                try {
                    filters.csvId = csvId;
                    const data = await bulkLoadService.getArticlesLoads(page, pageSize, filters);
                    return data;
                } catch (error) {
                    setArticles([]);
                }
            }
        },
        { keepPreviousData: true, refetchOnWindowFocus: false }
    );

    useEffect(() => {
        if (articlesDataLD) {
            const articles = articlesDataLD?.articles.map((item: any) => ({ ...item, checked: false }));
            setArticles(articles);
            setTotalElements(articlesDataLD?.totalElements);
        }
    }, [articlesDataLD]);

    const clickBack = (idCSV: any) => {
        navigate('/bulk-load-validation/' + idCSV);
    };

    const handleFilters = (filters: any) => {
        setFilters(filters);
        setPage(0);
        SetClean(false);
    };

    const handlePage = (page: number) => {
        setPage(page);
    };

    const handleItemSelected = (e: any, item: any) => {
        const checked = e.target.checked;
        const newList = articles.map((article) => {
            if (article.idArticle === item.idArticle) {
                return { ...article, checked };
            }
            return { ...article };
        });
        const atLeastChecked = newList.some((item) => item?.checked);
        setPartialCheck(atLeastChecked);
        setArticles(newList);
    };

    const handleDownloadAll = (checked: boolean) => {
        const newList = articles.map((item: any) => ({ ...item, checked }));
        setPartialCheck(false);
        setArticles(newList);
    };

    const [clean, SetClean] = useState(false);
    const handleCleanFilter = () => {
        SetClean(true);
    };

    return (
        <>
            <div className="layout__toolbar">
                <Toolbar />
            </div>
            <div className="container">
                <div className="logDisplay_button">
                    <Button
                        style={{ textTransform: 'none' }}
                        className="logDisplay_buttonBack"
                        startIcon={<ArrowBack />}
                        onClick={() => clickBack(csvId)}
                    >
                        {t('buttons.backFile')}
                    </Button>
                </div>
                <Box className="content_bulk-load-validation">
                    <Typography className="module_title" data-testid="module-title-bulk-load-validation" gutterBottom>
                        {t('titles.logDisplay')}
                    </Typography>
                </Box>
                <div className="create__controls">
                    <div className="create__line">
                        <FilterLogDisplay handleFilters={handleFilters} csvId={csvId} clean={clean} />
                    </div>
                </div>
                <Box className="content_bulk-load-validation">
                    <Typography className="module_title" data-testid="module-title" gutterBottom>
                        {t('titles.setState')}
                    </Typography>
                    <Typography className="module_subtitle1">{t('subtitles.subtitleSetState')}</Typography>
                </Box>
                <div className="mt-2">
                    <TableLogDisplay
                        articles={articles}
                        totalElements={totalElements}
                        isLoading={isLoadingArticles}
                        isFetching={isFetching}
                        page={page}
                        handlePage={handlePage}
                        pageSize={pageSize}
                        handleItemSelected={handleItemSelected}
                        partialCheck={isPartialCheck}
                        handleDownloadAll={handleDownloadAll}
                        handleCleanFilter={handleCleanFilter}
                    />
                </div>
            </div>
        </>
    );
};

export default LogDisplay;
