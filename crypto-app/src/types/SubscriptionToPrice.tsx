import { ComparisonType } from './ComparisonType';

export interface SubscriptionToPrice {
	id?: number;
	userId: number;
	currencyId: string;
	comparisonType: ComparisonType;
	price: number;
}
