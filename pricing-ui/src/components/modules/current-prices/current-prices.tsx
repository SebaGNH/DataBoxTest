import React, { useState, useEffect } from 'react';
import { useTranslation } from 'react-i18next';
import './current-prices.scss';
import Toolbar from '../../layout/Toolbar/Toolbar';
import { currentPricesService } from '../../../services/current-prices.service';
import { useQuery, useQueryClient } from 'react-query';
import TableCurrentPrices from './table-current-prices/table-current-prices';
import FilterCurrentPrices from './filter-current-prices/filter-current-prices';
import { Box, Typography } from '@mui/material';

const CurrentPrices = () => {
    const { t } = useTranslation();
    const [filters, setFilters] = useState<any>({});
    const [articles, setArticles] = useState<any[]>([]);
    const [articlesSelecteds, setArticlesSelecteds] = useState<any[]>([]);
    const [totalElements, setTotalElements] = useState<any[]>([]);
    const [isPartialCheck, setPartialCheck] = useState(false);
    const [isCheckedAll, setCheckedAll] = useState(false);
    const [page, setPage] = useState(0);
    const pageSize = 10;
    const [cancelRequest, setCancelRequest] = useState(false);
    const queryClient = useQueryClient();

    const {
        data: articlesData,
        isLoading: isLoadingArticles,
        isFetching
    } = useQuery(
        ['articles', page, pageSize, filters],
        async () => {
            const data = await currentPricesService.getArticlesCurrentPrices(page, pageSize, filters);
            return data;
        },
        {
            keepPreviousData: true,
            refetchOnWindowFocus: false,
            enabled: !cancelRequest,
            onError: () => {
                setCancelRequest(true);
            },
            retry: false,
            cacheTime: 0
        }
    );

    useEffect(() => {
        return () => {
            queryClient.cancelQueries('articles');
            setCancelRequest(true);
        };
    }, []);

    useEffect(() => {
        if (articlesData) {
            const idsArticles = articlesSelecteds.map((item: any) => item.idArticle);
            const articles = articlesData?.articles.map((item: any) => ({ ...item, checked: idsArticles.includes(item.idArticle) }));
            setArticles(articles);
            setTotalElements(articlesData?.totalElements);
            const atLeastChecked = articles.some((item: any) => item?.checked);
            const isCheckedAll = articles.every((item: any) => item?.checked);
            setPartialCheck(atLeastChecked);
            setCheckedAll(isCheckedAll);
        }
    }, [articlesData]);

    const handleFilters = (filters: any) => {
        setFilters(filters);
        setPage(0);
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
        if (!checked) {
            setArticlesSelecteds(articlesSelecteds.filter((article) => article.idArticle !== item.idArticle));
        } else {
            setArticlesSelecteds([...articlesSelecteds, { ...item, checked: true }]);
        }
        const atLeastChecked = newList.some((item) => item?.checked);
        setPartialCheck(atLeastChecked);
        setArticles(newList);
    };

    const handleCheckedAll = (e: any) => {
        const checked = e.target.checked;
        const newList = articles.map((item: any) => ({ ...item, checked }));
        setPartialCheck(false);
        if (!checked) {
            const idsArticles = articles.map((item: any) => item.idArticle);
            setArticlesSelecteds(articlesSelecteds.filter((item: any) => !idsArticles.includes(item.idArticle)));
        } else {
            setArticlesSelecteds([...articlesSelecteds, ...newList]);
        }
        setCheckedAll(checked);
        setArticles(newList);
    };
    console.log(articlesSelecteds);

    return (
        <>
            <div className="layout__toolbar">
                <Toolbar />
            </div>
            <div className="container">
                <Box className="content_current_prices">
                    <Typography className="module_title" data-testid="module-title" gutterBottom>
                        {t('titles.currenPrice')}
                    </Typography>
                    <Typography className="module_subtitle1">{t('subtitles.subtitlePrices')}</Typography>
                </Box>
                <div className="create__controls">
                    <div className="create__line">
                        <FilterCurrentPrices handleFilters={handleFilters} />
                    </div>
                </div>
                <div className="mt-2">
                    <TableCurrentPrices
                        articles={articles}
                        totalElements={totalElements}
                        isLoading={isLoadingArticles}
                        isFetching={isFetching}
                        page={page}
                        handlePage={handlePage}
                        pageSize={pageSize}
                        handleItemSelected={handleItemSelected}
                        partialCheck={isPartialCheck}
                        isCheckedAll={isCheckedAll}
                        handleCheckedAll={handleCheckedAll}
                        articlesSelecteds={articlesSelecteds}
                    />
                </div>
            </div>
        </>
    );
};

export default CurrentPrices;
