import { PageMetadata } from './PageMetadata';

export interface Page<T> {
	content: T[];
	page: PageMetadata;
}
