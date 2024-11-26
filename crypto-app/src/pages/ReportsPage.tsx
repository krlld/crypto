import React, { useEffect, useState } from 'react';
import { Card, Button, Input, Row, Col, Spin, Pagination, message, Avatar, DatePicker } from 'antd';
import axios from 'axios';
import { Report } from '../types/Report';
import { Page } from '../types/Page';
import { Profile } from '../types/Profile';
import { config } from '../config/request-config';
import { API_URL } from '../config/constants';
import { StarOutlined, StarFilled } from '@ant-design/icons';
import { CurrencyFilter } from '../types/CurrencyFilter';

const { RangePicker } = DatePicker;

const ReportsPage: React.FC = () => {
	const [reports, setReports] = useState<Report[]>([]);
	const [profiles, setProfiles] = useState<Profile[]>([]);
	const [loading, setLoading] = useState<boolean>(true);
	const [currentPage, setCurrentPage] = useState<number>(0);
	const [pageSize, setPageSize] = useState<number>(10);
	const [totalReports, setTotalReports] = useState<number>(0);
	const [favorites, setFavorites] = useState<Record<number, boolean>>({});
	const [filter, setFilter] = useState<CurrencyFilter>({});
	const [reportType, setReportType] = useState<'all' | 'my' | 'favorites'>('all');

	useEffect(() => {
		fetchReports();
	}, [currentPage, pageSize, filter, reportType]);

	const fetchReports = async () => {
		setLoading(true);
		try {
			let endpoint = '';
			switch (reportType) {
				case 'my':
					endpoint = `${API_URL}/data-analyze-service/reports/my-reports`;
					break;
				case 'favorites':
					endpoint = `${API_URL}/data-analyze-service/reports/favorites`;
					break;
				default:
					endpoint = `${API_URL}/data-analyze-service/reports`;
			}

			const response = await axios.get<Page<Report>>(endpoint, {
				params: {
					sort: 'id,desc',
					page: currentPage,
					size: pageSize,
					search: filter.search,
					createdAtDateStart: filter.createdAtDateStart,
					createdAtDateEnd: filter.createdAtDateEnd,
					isPublic: filter.isPublic,
					userIds: filter.userIds?.join(','),
				},
				...config,
			});
			setReports(response.data.content);
			setTotalReports(response.data.page.totalElements);
			fetchProfiles(response.data.content.map((report) => report.userId || 0));
			fetchFavorites(response.data.content.map((report) => report.id || 0));
		} catch (error) {
			message.error('Ошибка загрузки отчетов.');
		} finally {
			setLoading(false);
		}
	};

	const fetchFavorites = async (reportIds: number[]) => {
		if (reportIds.length === 0) return;
		try {
			const reportIdsParam = reportIds.join(',');
			const response = await axios.get<Record<number, boolean>>(
				`${API_URL}/data-analyze-service/reports/is-in-favorite-by-ids?ids=${reportIdsParam}`,
				config
			);
			setFavorites(response.data);
		} catch (error) {
			message.error('Ошибка загрузки избранных отчетов.');
		}
	};

	const fetchProfiles = async (userIds: number[]) => {
		if (userIds.length === 0) return;
		try {
			const userIdsParam = userIds.join(',');
			const response = await axios.get<Profile[]>(
				`${API_URL}/auth-service/users/profiles-by-ids?ids=${userIdsParam}`,
				config
			);
			setProfiles(response.data);
		} catch (error) {
			message.error('Ошибка загрузки профилей пользователей.');
		}
	};

	const handleSearch = (e: React.ChangeEvent<HTMLInputElement>) => {
		setFilter({ ...filter, search: e.target.value });
		setCurrentPage(0);
	};

	const handleDateChange = (dates: any) => {
		if (dates) {
			setFilter({
				...filter,
				createdAtDateStart: dates[0].format('YYYY-MM-DD'),
				createdAtDateEnd: dates[1].format('YYYY-MM-DD'),
			});
		}
	};

	const handleDownload = (fileId: string) => {
		window.open(`${API_URL}/file-service/files/${fileId}/download`, '_blank');
	};

	const handleToggleFavorite = async (reportId: number) => {
		try {
			await axios.patch(`${API_URL}/data-analyze-service/reports/${reportId}/favorite`, {}, config);
			setFavorites((prev) => ({ ...prev, [reportId]: !prev[reportId] }));
			message.success(
				favorites[reportId] ? 'Отчет добавлен в избранное.' : 'Отчет убран из избранного.'
			);
		} catch (error) {
			message.error('Ошибка при добавлении в избранное.');
		}
	};

	const handlePageChange = (page: number, pageSize: number) => {
		setCurrentPage(page - 1);
		setPageSize(pageSize);
	};

	const handlePageSizeChange = (current: number, size: number) => {
		setPageSize(size);
		setCurrentPage(0);
	};

	const handleReportTypeChange = (type: 'all' | 'my' | 'favorites') => {
		setReportType(type);
		setCurrentPage(0);
	};

	return (
		<div style={{ padding: '20px' }}>
			<h2>Отчеты</h2>
			<div style={{ marginBottom: 16 }}>
				<Button
					type={reportType === 'all' ? 'primary' : 'default'}
					onClick={() => handleReportTypeChange('all')}
					style={{ marginRight: 8 }}
				>
					Все публичные отчеты
				</Button>
				<Button
					type={reportType === 'my' ? 'primary' : 'default'}
					onClick={() => handleReportTypeChange('my')}
					style={{ marginRight: 8 }}
				>
					Мои отчеты
				</Button>
				<Button
					type={reportType === 'favorites' ? 'primary' : 'default'}
					onClick={() => handleReportTypeChange('favorites')}
				>
					Избранные отчеты
				</Button>
			</div>
			<Input placeholder="Поиск по названию" onChange={handleSearch} style={{ marginBottom: 16 }} />
			<RangePicker onChange={handleDateChange} style={{ marginBottom: 16 }} />
			{loading ? (
				<Spin />
			) : (
				<Row gutter={24}>
					{reports.map((report) => {
						const authorProfile = profiles.find((profile) => profile.id === report.userId);
						return (
							<Col span={6} key={report.id} style={{ marginBottom: 16 }}>
								<Card
									hoverable
									title={report.title}
									bordered={false}
									extra={
										<Button
											type="text"
											icon={
												favorites[report.id || 0] ? (
													<StarFilled style={{ color: 'gold' }} />
												) : (
													<StarOutlined />
												)
											}
											onClick={() => handleToggleFavorite(report.id || 0)}
											style={{ marginLeft: 'auto' }}
										/>
									}
								>
									<div style={{ display: 'flex', alignItems: 'center', marginBottom: 10 }}>
										<Avatar
											src={
												authorProfile
													? `${API_URL}/file-service/files/${authorProfile.avatarId}/download`
													: undefined
											}
											alt="Avatar"
											size={40}
										/>
										<div style={{ marginLeft: 10 }}>
											<p style={{ margin: 0 }}>
												<strong>Автор:</strong>{' '}
												{authorProfile
													? `${authorProfile.name} ${authorProfile.lastname}`
													: 'Неизвестен'}
											</p>
										</div>
									</div>
									<p>
										<strong>Дата создания:</strong> {report.createdAtDate || 'Не указана'}
									</p>
									<p style={{ marginBottom: 10 }}>
										<strong>Описание:</strong> {report.description || 'Нет описания'}
									</p>

									<Button block onClick={() => handleDownload(report.sourceFileId)}>
										Скачать исходный файл
									</Button>
									{report.resultFileId && (
										<Button
											type="primary"
											block
											onClick={() => handleDownload(report.resultFileId || ' ')}
											style={{ marginTop: 10 }}
										>
											Скачать файл с результатами
										</Button>
									)}
								</Card>
							</Col>
						);
					})}
				</Row>
			)}
			<Pagination
				current={currentPage + 1}
				pageSize={pageSize}
				total={totalReports}
				onChange={handlePageChange}
				onShowSizeChange={handlePageSizeChange}
				showSizeChanger
				style={{ marginTop: '20px', textAlign: 'center' }}
			/>
		</div>
	);
};

export default ReportsPage;
