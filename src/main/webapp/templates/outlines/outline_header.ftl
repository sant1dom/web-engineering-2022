<!-- app-Header -->
<div class="app-header header sticky">
    <div class="container-fluid main-container">
        <div class="d-flex">
            <a aria-label="Hide Sidebar" class="app-sidebar__toggle" data-bs-toggle="sidebar"
               href="javascript:void(0)"></a>
            <!-- sidebar-toggle-->
            <a class="logo-horizontal " href="../index.ftl">
                <img src="/assets/images/brand/logo.png" class="header-brand-img desktop-logo" alt="logo">
                <img src="/assets/images/brand/logo-3.png" class="header-brand-img light-logo1"
                     alt="logo">
            </a>
            <!-- LOGO -->
            <div class="main-header-center ms-3 d-none d-lg-block">
                <label>
                    <input type="text" class="form-control" id="typehead" placeholder="Search for results.."
                              autocomplete="off">
                </label>
                <button class="btn px-0 pt-2"><i class="fe fe-search" aria-hidden="true"></i></button>
            </div>
            <div class="d-flex order-lg-2 ms-auto header-right-icons">
                <div class="dropdown d-none">
                    <a href="javascript:void(0)" class="nav-link icon" data-bs-toggle="dropdown">
                        <i class="fe fe-search"></i>
                    </a>
                    <div class="dropdown-menu header-search dropdown-menu-start">
                        <div class="input-group w-100 p-2">
                            <label>
                                <input type="text" class="form-control" placeholder="Search..">
                            </label>
                            <div class="input-group-text btn btn-primary">
                                <i class="fe fe-search" aria-hidden="true"></i>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- SEARCH -->
                <button class="navbar-toggler navresponsive-toggler d-lg-none ms-auto" type="button"
                        data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent-4"
                        aria-controls="navbarSupportedContent-4" aria-expanded="false"
                        aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon fe fe-more-vertical"></span>
                </button>
                <div class="navbar navbar-collapse responsive-navbar p-0">
                    <div class="collapse navbar-collapse" id="navbarSupportedContent-4">
                        <div class="d-flex order-lg-2">
                            <div class="dropdown d-lg-none d-flex">
                                <a href="javascript:void(0)" class="nav-link icon" data-bs-toggle="dropdown">
                                    <i class="fe fe-search"></i>
                                </a>
                                <div class="dropdown-menu header-search dropdown-menu-start">
                                    <div class="input-group w-100 p-2">
                                        <label>
                                            <input type="text" class="form-control" placeholder="Search..">
                                        </label>
                                        <div class="input-group-text btn btn-primary">
                                            <i class="fa fa-search" aria-hidden="true"></i>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!-- Theme-Layout -->

                            <!-- NOTIFICATIONS -->
                            <div class="dropdown  d-flex message">
                                <a class="nav-link icon text-center" data-bs-toggle="dropdown">
                                    <i class="fe fe-message-square"></i><span class="pulse-danger"></span>
                                </a>
                                <div class="dropdown-menu dropdown-menu-end dropdown-menu-arrow">
                                    <div class="drop-heading border-bottom">
                                        <div class="d-flex">
                                            <h6 class="mt-1 mb-0 fs-16 fw-semibold text-dark">You have 5
                                                Messages</h6>
                                            <div class="ms-auto">
                                                <a href="javascript:void(0)" class="text-muted p-0 fs-12">make
                                                    all unread</a>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="message-menu message-menu-scroll">
                                        <a class="dropdown-item d-flex" href="chat.html">
                                            <span class="avatar avatar-md brround me-3 align-self-center cover-image"
                                                                data-bs-image-src="/assets/images/users/1.jpg"></span>
                                            <div class="wd-90p">
                                                <div class="d-flex">
                                                    <h5 class="mb-1">Peter Theil</h5>
                                                    <small class="text-muted ms-auto text-end">
                                                        6:45 am
                                                    </small>
                                                </div>
                                                <span>Commented on file Guest list..</span>
                                            </div>
                                        </a>
                                    </div>
                                    <div class="dropdown-divider m-0"></div>
                                    <a href="javascript:void(0)"
                                       class="dropdown-item text-center p-3 text-muted">See all
                                        Messages</a>
                                </div>
                            </div>
                            <!-- SIDE-MENU -->
                            <div class="dropdown d-flex profile-1">
                                <a href="javascript:void(0)" data-bs-toggle="dropdown"
                                   class="nav-link leading-none d-flex">
                                    <img src="/assets/images/users/21.jpg" alt="profile-user"
                                         class="avatar  profile-user brround cover-image">
                                </a>
                                <div class="dropdown-menu dropdown-menu-end dropdown-menu-arrow">
                                    <div class="drop-heading">
                                        <div class="text-center">
                                            <h5 class="text-dark mb-0 fs-14 fw-semibold">Percy Kewshun</h5>
                                            <small class="text-muted">Senior Admin</small>
                                        </div>
                                    </div>
                                    <div class="dropdown-divider m-0"></div>
                                    <a class="dropdown-item" href="../profile.html">
                                        <i class="dropdown-icon fe fe-user"></i> Profile
                                    </a>
                                    <a class="dropdown-item" href="../email-inbox.html">
                                        <i class="dropdown-icon fe fe-mail"></i> Inbox
                                        <span class="badge bg-danger rounded-pill float-end">5</span>
                                    </a>
                                    <a class="dropdown-item" href="./login.ftl">
                                        <i class="dropdown-icon fe fe-alert-circle"></i> Sign out
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- /app-Header -->
