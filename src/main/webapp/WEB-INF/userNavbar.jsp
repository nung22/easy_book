<nav class="navbar navbar-expand-lg user-navbar">
    <div class="container-fluid">
        <a class="navbar-brand" href="/">EasyBook</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <div class="spacer"></div>
            <form class="d-flex input-group bg-light search" role="search">
                <input class="form-control search ps-3" type="search" placeholder="Search by Title, Author or ISBN" aria-label="Search">
                <button class="btn btn-outline-success search pe-3" type="submit">Search</button>
            </form>
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item ms-5 text-center mt-1">
                    <c:if test="${}">
                    </c:if>
                    <h6 class="greeting fw-bold text-decoration-underline">Hello, <c:out value="${user.firstName}"></c:out></h6>
                    {% endif %}
                    <a class="nav-link fs-5 fw-bold" aria-current="page" onmouseover="addActive(this)" onmouseout="removeActive(this)" href="/account">Account</a>
                </li>
                <li class="nav-item ms-3">
                    <a class="nav-link" href="#"><img id="cart" src="{{ url_for('static', filename='assets/icons/shopping_cart.png') }}" alt="image of shopping cart"></a>
                </li>
                {% if session.is_admin == 1 %}
                <li class="nav-item ms-4 mt-2">
                    <a class="btn btn-warning btn-sm admin-tools fw-bold" aria-current="page" href="/admin_dashboard">Admin Tools</a>
                </li>
                {% endif %}
            </ul>
        </div>
    </div>
</nav>

<div class="genres bg-dark">
    <div class="genre p-1" onmouseover="addPill(this)" onmouseout="removePill(this)"><a href="/all_books" class="nav-link mx-1">ALL BOOKS</a></div>
    <div class="genre p-1" onmouseover="addPill(this)" onmouseout="removePill(this)"><a href="/cookbooks" class="nav-link mx-1">COOKBOOKS</a></div>
    <div class="genre p-1" onmouseover="addPill(this)" onmouseout="removePill(this)"><a href="/fantasy_scifi" class="nav-link mx-1">FANTASY & SCI-FI</a></div>
    <div class="genre p-1" onmouseover="addPill(this)" onmouseout="removePill(this)"><a href="/horror" class="nav-link mx-1">HORROR</a></div>
    <div class="genre p-1" onmouseover="addPill(this)" onmouseout="removePill(this)"><a href="/manga_graphic_novels" class="nav-link mx-1">MANGA & GRAPHIC NOVELS</a></div>
    <div class="genre p-1" onmouseover="addPill(this)" onmouseout="removePill(this)"><a href="/mystery_crime" class="nav-link mx-1">MYSTERY & CRIME</a></div>
</div>