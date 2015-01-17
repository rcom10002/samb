package info.woody.so.bean;

import java.util.List;

public class Pagination {

	public static Pagination getEntityPagination() {
		return new Pagination(ROWS_IN_ENTITY_PAGE);
	}

	public static Pagination getLinkedPagination() {
		return new Pagination(ROWS_IN_LINKED_PAGE);
	}

	private static int ROWS_IN_ENTITY_PAGE = 10;
	private static int ROWS_IN_LINKED_PAGE = 100;

	protected int rowsInPage;

	protected int currentPageNumber;
	protected int totalPageNumbers;
	protected int rowCount;

	private Pagination(int rowsInPage) {
		this.rowsInPage = rowsInPage;
	}

	public int getCurrentPageNumber() {
		return currentPageNumber;
	}

	public int getTotalPageNumbers() {
		return totalPageNumbers;
	}

	public int getPageSize() {
		return rowsInPage;
	}

	public int getRowCount() {
		return rowCount;
	}

	public class Page<T> {
		Pagination pagination;
		List<T> list;
		public Page(List<T> list) {
			super();
			this.pagination = Pagination.this;
			this.list = list;
		}
		public Pagination getPagination() {
			return pagination;
		}
		public List<T> getList() {
			return list;
		}
	}

	public class InternalPagination extends Pagination {

		public InternalPagination(int currentPageNumber, int rowCount) {
			super(ROWS_IN_ENTITY_PAGE);
			this.currentPageNumber = currentPageNumber;
			this.setRowCount(rowCount);
			Pagination.this.currentPageNumber = currentPageNumber;
			Pagination.this.totalPageNumbers = totalPageNumbers;
			Pagination.this.rowCount = rowCount;
		}

		public int getLimit() {
			return rowsInPage;
		}

		public int getOffset() {
			return (currentPageNumber - 1) * rowsInPage;
		}

		private void setRowCount(int rowCount) {
			if (rowCount == 0) {
				currentPageNumber = 1;
				totalPageNumbers = 1;
				return;
			}
			if (currentPageNumber < 0) {
				currentPageNumber = 1;
			}
			totalPageNumbers = (rowCount / rowsInPage) + (rowCount % rowsInPage > 0 ? 1 : 0);
			if (currentPageNumber < 0) {
				currentPageNumber = 1;
			} else if (currentPageNumber > totalPageNumbers) {
				currentPageNumber = totalPageNumbers;
			}
		}
	}

}

