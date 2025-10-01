<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Notice List</title>
    <!-- Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Bootstrap Icons -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css" rel="stylesheet">
    <style>
        .notice-header {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 2rem 0;
            margin-bottom: 2rem;
        }
        .notice-card {
            transition: transform 0.2s ease-in-out, box-shadow 0.2s ease-in-out;
        }
        .notice-card:hover {
            transform: translateY(-2px);
            box-shadow: 0 4px 15px rgba(0,0,0,0.1);
        }
        .table-hover tbody tr:hover {
            background-color: #f8f9fa;
        }
        .btn-create {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            border: none;
            padding: 12px 30px;
            border-radius: 25px;
            transition: all 0.3s ease;
        }
        .btn-create:hover {
            transform: translateY(-1px);
            box-shadow: 0 4px 15px rgba(102, 126, 234, 0.4);
        }
        .pagination .page-link {
            border-radius: 8px;
            margin: 0 2px;
            border: 1px solid #dee2e6;
            color: #667eea;
        }
        .pagination .page-item.active .page-link {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            border-color: #667eea;
        }
        .pagination .page-link:hover {
            background-color: #f8f9fa;
            border-color: #667eea;
            color: #667eea;
        }
    </style>
</head>
<body class="bg-light">
    <!-- Header -->
    <div class="notice-header">
        <div class="container">
            <div class="row align-items-center">
                <div class="col">
                    <h1 class="mb-0">
                        <i class="bi bi-megaphone me-3"></i>
                        <spring:message code='notice.head'/>
                    </h1>
                    <p class="mb-0 mt-2 opacity-75">공지사항을 확인하고 관리하세요</p>
                </div>
                <div class="col-auto">
                    <a href="/notice/form" class="btn btn-create btn-light text-primary fw-bold">
                        <i class="bi bi-plus-circle me-2"></i>
                        <spring:message code='notice.new'/>
                    </a>
                </div>
            </div>
        </div>
    </div>

    <!-- Main Content -->
    <div class="container">
        <!-- Search Section -->
        <div class="row mb-4">
            <div class="col-12">
                <div class="card shadow-sm border-0">
                    <div class="card-body">
                        <form method="get" action="/notice/list" class="d-flex gap-2">
                            <div class="flex-grow-1">
                                <input type="text" name="keyword" class="form-control" 
                                       placeholder="제목 또는 내용으로 검색하세요..." 
                                       value="${keyword}" maxlength="50">
                            </div>
                            <button type="submit" class="btn btn-primary">
                                <i class="bi bi-search me-1"></i>검색
                            </button>
                            <c:if test="${not empty keyword}">
                                <a href="/notice/list" class="btn btn-outline-secondary">
                                    <i class="bi bi-x-circle me-1"></i>초기화
                                </a>
                            </c:if>
                        </form>
                        <c:if test="${not empty keyword}">
                            <div class="mt-2">
                                <small class="text-muted">
                                    '<strong>${keyword}</strong>' 검색 결과
                                </small>
                            </div>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
        
        <div class="row">
            <div class="col-12">
                <div class="card notice-card shadow-sm border-0">
                    <div class="card-body p-0">
                        <c:choose>
                            <c:when test="${not empty noticeList}">
                                <div class="table-responsive">
                                    <table class="table table-hover mb-0">
                                        <thead class="table-light">
                                            <tr>
                                                <th scope="col" class="text-center" style="width: 10%;">
                                                    <i class="bi bi-hash text-muted me-1"></i>
                                                    <spring:message code='notice.seq'/>
                                                </th>
                                                <th scope="col" style="width: 60%;">
                                                    <i class="bi bi-file-text text-muted me-1"></i>
                                                    <spring:message code='notice.title'/>
                                                </th>
                                                <th scope="col" class="text-center" style="width: 20%;">
                                                    <i class="bi bi-person text-muted me-1"></i>
                                                    <spring:message code='notice.user'/>
                                                </th>
                                                <th scope="col" class="text-center" style="width: 10%;">
                                                    <i class="bi bi-eye text-muted me-1"></i>
                                                    액션
                                                </th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="notice" items="${noticeList}" varStatus="status">
                                                <tr>
                                                    <td class="text-center fw-bold text-primary">${notice.seq}</td>
                                                    <td>
                                                        <a href="/notice/${notice.seq}" 
                                                           class="text-decoration-none text-dark fw-medium">
                                                            ${notice.title}
                                                        </a>
                                                    </td>
                                                    <td class="text-center">
                                                        <span class="badge bg-secondary">${notice.userId}</span>
                                                    </td>
                                                    <td class="text-center">
                                                        <a href="/notice/${notice.seq}" 
                                                           class="btn btn-outline-primary btn-sm">
                                                            <i class="bi bi-eye"></i>
                                                        </a>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                                
                                <!-- 페이징 -->
                                <c:if test="${paging.totalPage > 1}">
                                    <div class="d-flex justify-content-center mt-4 mb-3">
                                        <nav aria-label="Page navigation">
                                            <ul class="pagination pagination-sm">
                                                <!-- 이전 페이지 그룹 -->
                                                <c:if test="${paging.prev}">
                                                    <li class="page-item">
                                                        <a class="page-link" href="/notice/list?page=${paging.startPage - 1}" aria-label="Previous">
                                                            <i class="bi bi-chevron-double-left"></i>
                                                        </a>
                                                    </li>
                                                </c:if>
                                                
                                                <!-- 이전 페이지 -->
                                                <c:if test="${paging.page > 1}">
                                                    <li class="page-item">
                                                        <a class="page-link" href="/notice/list?page=${paging.page - 1}" aria-label="Previous">
                                                            <i class="bi bi-chevron-left"></i>
                                                        </a>
                                                    </li>
                                                </c:if>
                                                
                                                <!-- 페이지 번호 -->
                                                <c:forEach var="pageNum" begin="${paging.startPage}" end="${paging.endPage}">
                                                    <li class="page-item ${pageNum == paging.page ? 'active' : ''}">
                                                        <a class="page-link" href="/notice/list?page=${pageNum}">${pageNum}</a>
                                                    </li>
                                                </c:forEach>
                                                
                                                <!-- 다음 페이지 -->
                                                <c:if test="${paging.page < paging.totalPage}">
                                                    <li class="page-item">
                                                        <a class="page-link" href="/notice/list?page=${paging.page + 1}" aria-label="Next">
                                                            <i class="bi bi-chevron-right"></i>
                                                        </a>
                                                    </li>
                                                </c:if>
                                                
                                                <!-- 다음 페이지 그룹 -->
                                                <c:if test="${paging.next}">
                                                    <li class="page-item">
                                                        <a class="page-link" href="/notice/list?page=${paging.endPage + 1}" aria-label="Next">
                                                            <i class="bi bi-chevron-double-right"></i>
                                                        </a>
                                                    </li>
                                                </c:if>
                                            </ul>
                                        </nav>
                                    </div>
                                </c:if>
                                
                                <!-- 페이징 정보 표시 -->
                                <div class="text-center text-muted small mb-3">
                                    총 ${paging.totalCount}개의 게시물 중 ${paging.page}/${paging.totalPage} 페이지
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="text-center py-5">
                                    <i class="bi bi-inbox display-1 text-muted mb-3"></i>
                                    <h4 class="text-muted">등록된 공지사항이 없습니다</h4>
                                    <p class="text-muted">새로운 공지사항을 작성해보세요.</p>
                                    <a href="/notice/form" class="btn btn-create text-white">
                                        <i class="bi bi-plus-circle me-2"></i>
                                        <spring:message code='notice.new'/>
                                    </a>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Footer -->
    <footer class="mt-5 py-4 bg-white border-top">
        <div class="container">
            <div class="row">
                <div class="col text-center text-muted">
                    <small>© 2025 Notice Management System. All rights reserved.</small>
                </div>
            </div>
        </div>
    </footer>

    <!-- Bootstrap 5 JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>