<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Notice Detail</title>
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
        .form-card {
            transition: transform 0.2s ease-in-out, box-shadow 0.2s ease-in-out;
            border: none;
            box-shadow: 0 2px 20px rgba(0,0,0,0.1);
        }
        .form-card:hover {
            transform: translateY(-2px);
            box-shadow: 0 4px 25px rgba(0,0,0,0.15);
        }
        .form-label {
            font-weight: 600;
            color: #495057;
            margin-bottom: 0.75rem;
        }
        .form-control:focus {
            border-color: #667eea;
            box-shadow: 0 0 0 0.2rem rgba(102, 126, 234, 0.25);
        }
        .btn-primary {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            border: none;
            padding: 12px 30px;
            border-radius: 25px;
            font-weight: 600;
            transition: all 0.3s ease;
        }
        .btn-primary:hover {
            transform: translateY(-1px);
            box-shadow: 0 4px 15px rgba(102, 126, 234, 0.4);
        }
        .btn-secondary {
            border-radius: 25px;
            padding: 12px 30px;
            font-weight: 600;
            transition: all 0.3s ease;
        }
        .btn-secondary:hover {
            transform: translateY(-1px);
        }
        .breadcrumb {
            background: transparent;
            padding: 0;
        }
        .breadcrumb-item + .breadcrumb-item::before {
            content: ">";
            color: #6c757d;
        }
    </style>
</head>
<body class="bg-light">
    <!-- Header -->
    <div class="notice-header">
        <div class="container">
            <div class="row align-items-center">
                <div class="col">
                    <nav aria-label="breadcrumb">
                        <ol class="breadcrumb text-white-50 mb-2">
                            <li class="breadcrumb-item">
                                <a href="/notice/list" class="text-white text-decoration-none">
                                    <i class="bi bi-house-door me-1"></i>공지사항
                                </a>
                            </li>
                            <li class="breadcrumb-item active text-white" aria-current="page">
                                <c:choose>
                                    <c:when test="${not empty notice}">수정</c:when>
                                    <c:otherwise>작성</c:otherwise>
                                </c:choose>
                            </li>
                        </ol>
                    </nav>
                    <h1 class="mb-0">
                        <i class="bi bi-pencil-square me-3"></i>
                        <spring:message code='notice.detail.head'/>
                    </h1>
                    <p class="mb-0 mt-2 opacity-75">
                        <c:choose>
                            <c:when test="${not empty notice}">공지사항을 수정하세요</c:when>
                            <c:otherwise>새로운 공지사항을 작성하세요</c:otherwise>
                        </c:choose>
                    </p>
                </div>
            </div>
        </div>
    </div>

    <!-- Main Content -->
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-lg-8 col-md-10">
                <div class="card form-card">
                    <div class="card-header bg-white py-4">
                        <h5 class="card-title mb-0">
                            <i class="bi bi-file-text text-primary me-2"></i>
                            <c:choose>
                                <c:when test="${not empty notice}">공지사항 수정</c:when>
                                <c:otherwise>공지사항 작성</c:otherwise>
                            </c:choose>
                        </h5>
                    </div>
                    <div class="card-body p-4">
                        <form name="frmNotice" method="post" action="/notice/save" novalidate>
                            <c:if test="${not empty notice}">
                                <input type="hidden" name="seq" value="${notice.seq}" />
                            </c:if>
                            
                            <!-- Title Field -->
                            <div class="mb-4">
                                <label for="title" class="form-label">
                                    <i class="bi bi-type text-primary me-2"></i>
                                    <spring:message code='notice.title'/>
                                    <span class="text-danger">*</span>
                                </label>
                                <input type="text" 
                                       class="form-control form-control-lg" 
                                       id="title"
                                       name="title" 
                                       maxlength="80" 
                                       value="${notice.title}" 
                                       placeholder="공지사항 제목을 입력하세요"
                                       required>
                                <div class="invalid-feedback">
                                    제목을 입력해주세요.
                                </div>
                            </div>

                            <!-- Content Field -->
                            <div class="mb-4">
                                <label for="content" class="form-label">
                                    <i class="bi bi-card-text text-primary me-2"></i>
                                    <spring:message code='notice.content'/>
                                    <span class="text-danger">*</span>
                                </label>
                                <textarea class="form-control" 
                                          id="content"
                                          name="content" 
                                          rows="12" 
                                          placeholder="공지사항 내용을 입력하세요"
                                          required>${notice.content}</textarea>
                                <div class="invalid-feedback">
                                    내용을 입력해주세요.
                                </div>
                                <div class="form-text">
                                    <i class="bi bi-info-circle me-1"></i>
                                    상세한 내용을 작성해주세요.
                                </div>
                            </div>

                            <!-- Action Buttons -->
                            <div class="d-flex justify-content-between align-items-center pt-3">
                                <a href="/notice/list" class="btn btn-secondary">
                                    <i class="bi bi-arrow-left me-2"></i>
                                    목록으로
                                </a>
                                <div>
                                    <button type="reset" class="btn btn-outline-secondary me-2">
                                        <i class="bi bi-arrow-clockwise me-2"></i>
                                        초기화
                                    </button>
                                    <button type="submit" class="btn btn-primary">
                                        <i class="bi bi-check-circle me-2"></i>
                                        <spring:message code='notice.submit'/>
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>

                <!-- Additional Info Card -->
                <div class="card mt-4 border-0 bg-light">
                    <div class="card-body text-center py-3">
                        <small class="text-muted">
                            <i class="bi bi-lightbulb me-1"></i>
                            <strong>팁:</strong> 제목과 내용을 명확하게 작성하여 독자가 쉽게 이해할 수 있도록 해주세요.
                        </small>
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
    
    <!-- Form Validation Script -->
    <script>
        // Bootstrap form validation
        (function() {
            'use strict';
            window.addEventListener('load', function() {
                var forms = document.getElementsByClassName('needs-validation');
                var validation = Array.prototype.filter.call(forms, function(form) {
                    form.addEventListener('submit', function(event) {
                        if (form.checkValidity() === false) {
                            event.preventDefault();
                            event.stopPropagation();
                        }
                        form.classList.add('was-validated');
                    }, false);
                });
            }, false);
        })();

        // Real-time validation
        document.addEventListener('DOMContentLoaded', function() {
            const form = document.querySelector('form[name="frmNotice"]');
            const titleInput = document.getElementById('title');
            const contentInput = document.getElementById('content');

            function validateField(field) {
                if (field.value.trim() === '') {
                    field.classList.add('is-invalid');
                    field.classList.remove('is-valid');
                } else {
                    field.classList.add('is-valid');
                    field.classList.remove('is-invalid');
                }
            }

            titleInput.addEventListener('blur', function() {
                validateField(this);
            });

            contentInput.addEventListener('blur', function() {
                validateField(this);
            });

            form.addEventListener('submit', function(e) {
                let isValid = true;
                
                if (titleInput.value.trim() === '') {
                    titleInput.classList.add('is-invalid');
                    isValid = false;
                }
                
                if (contentInput.value.trim() === '') {
                    contentInput.classList.add('is-invalid');
                    isValid = false;
                }

                if (!isValid) {
                    e.preventDefault();
                    e.stopPropagation();
                    
                    // Show alert
                    const alertDiv = document.createElement('div');
                    alertDiv.className = 'alert alert-danger alert-dismissible fade show mt-3';
                    alertDiv.innerHTML = `
                        <i class="bi bi-exclamation-triangle me-2"></i>
                        <strong>입력 오류!</strong> 모든 필수 항목을 입력해주세요.
                        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                    `;
                    form.insertBefore(alertDiv, form.firstChild);
                    
                    // Scroll to top of form
                    form.scrollIntoView({ behavior: 'smooth' });
                }
            });
        });
    </script>
</body>
</html>