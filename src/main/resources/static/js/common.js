let deleteUrl = '';
function openDeleteModal(event, url) {
    event.preventDefault();
    deleteUrl = url;
}
document.getElementById('confirmDelete').addEventListener('click', function () {
    if (deleteUrl) {
        window.location.href = deleteUrl;
    }
});
function changePageSize() {
    $("#searchForm").submit();
}