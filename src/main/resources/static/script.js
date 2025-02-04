const API_URL = window.location.origin + '/api';

async function fetchPosts() {
    try {
        const response = await fetch(`${API_URL}/post/getallpost`);
        const posts = await response.json();
        const postList = document.getElementById('postList');
        postList.innerHTML = '';
        posts.forEach(post => {
            const postElement = document.createElement('div');
            postElement.classList.add('post');
            postElement.innerHTML = `
                <h3>${post.title}</h3>
                <p><strong>Yazar:</strong> ${post.author}</p>
                <p class="timestamp">Oluşturulma Zamanı: ${new Date(post.createdAt).toLocaleString('tr-TR', {
                year: 'numeric',
                month: '2-digit',
                day: '2-digit',
                hour: '2-digit',
                minute: '2-digit',
                second: '2-digit'
            })}</p>
                <p>${post.content}</p>
                <button onclick="toggleComments(${post.postID})">Yorumları Göster/Gizle</button>
                <div id="comments-${post.postID}" class="comments" style="display: none;">
                    <h4>Yorumlar</h4>
                    <div id="commentList-${post.postID}"></div>
                    <div class="new-comment">
                        <input type="text" id="newCommentAuthor-${post.postID}" placeholder="Adınız" required>
                        <textarea id="newComment-${post.postID}" placeholder="Yorum yazın" required></textarea>
                        <button class="comment-button" onclick="addComment(${post.postID})">Yorum Ekle</button>
                    </div>
                </div>
            `;
            postList.appendChild(postElement);
        });
    } catch (error) {
        console.error('Gönderiler alınırken hata oluştu:', error);
    }
}

async function fetchComments(postId) {
    try {
        const postResponse = await fetch(`${API_URL}/post/getPostById/${postId}`);
        const post = await postResponse.json();
        console.log('Mevcut post:', post);

        const commentsResponse = await fetch(`${API_URL}/comment/getallcomment`);
        const comments = await commentsResponse.json();
        console.log('Tüm yorumlar:', comments);

        const postComments = comments.filter(comment =>
                post.comments && post.comments.some(postComment =>
                    postComment.commentID === comment.commentID
                )
        );
        console.log('Filtrelenmiş yorumlar:', postComments);

        const commentList = document.getElementById(`commentList-${postId}`);
        commentList.innerHTML = '';

        if (postComments.length === 0) {
            commentList.innerHTML = '<p>Henüz yorum yapılmamış.</p>';
            return;
        }

        postComments.forEach(comment => {
            const commentElement = document.createElement('div');
            commentElement.classList.add('comment');
            commentElement.innerHTML = `
                <p><strong>${comment.author || 'Anonim'}:</strong> ${comment.text || 'Boş yorum'}</p>
                <p><small>Oluşturulma Zamanı: ${new Date(comment.createdAt).toLocaleString('tr-TR')}</small></p>
            `;
            commentList.appendChild(commentElement);
        });
    } catch (error) {
        console.error('Yorumlar alınırken hata oluştu:', error);
        const commentList = document.getElementById(`commentList-${postId}`);
        commentList.innerHTML = '<p>Yorumlar yüklenirken bir hata oluştu.</p>';
    }
}

async function addComment(postId) {
    const commentText = document.getElementById(`newComment-${postId}`).value;
    const commentAuthor = document.getElementById(`newCommentAuthor-${postId}`).value;
    if (!commentText.trim()) {
        alert('Yorum boş olamaz!');
        return;
    }
    if (!commentAuthor.trim()) {
        alert('Yazar adı boş olamaz!');
        return;
    }

    try {
        const postResponse = await fetch(`${API_URL}/post/getPostById/${postId}`);
        const post = await postResponse.json();

        const response = await fetch(`${API_URL}/comment/createComment`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                text: commentText,
                author: commentAuthor,
                post: {
                    postID: postId,
                    title: post.title,
                    author: post.author,
                    content: post.content
                }
            }),
        });

        if (response.ok) {
            const result = await response.json();
            console.log('Yorum başarıyla eklendi:', result);
            document.getElementByI

            d(`newComment-${postId}`).value = '';
            document.getElementById(`newCommentAuthor-${postId}`).value = '';

            setTimeout(() => {
                fetchComments(postId);
            }, 500);
        } else {
            const errorData = await response.json();
            console.error('Yorum eklenirken hata oluştu:', errorData);
            alert('Yorum eklenirken bir hata oluştu. Lütfen tekrar deneyin.');
        }
    } catch (error) {
        console.error('Yorum eklenirken hata oluştu:', error);
        alert('Yorum eklenirken bir hata oluştu. Lütfen tekrar deneyin.');
    }
}

function toggleComments(postId) {
    const commentsSection = document.getElementById(`comments-${postId}`);
    if (commentsSection.style.display === 'none') {
        commentsSection.style.display = 'block';
        fetchComments(postId);
    } else {
        commentsSection.style.display = 'none';
    }
}

async function createPost(title, author, content) {
    try {
        const response = await fetch(`${API_URL}/post/createPost`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ title, author, content }),
        });
        if (response.ok) {
            fetchPosts();
            document.getElementById('postForm').reset();
            toggleNewPostForm();
        } else {
            console.error('Gönderi oluşturulurken hata oluştu');
        }
    } catch (error) {
        console.error('Gönderi oluşturulurken hata oluştu:', error);
    }
}

function toggleNewPostForm() {
    const newPostSection = document.getElementById('newPost');
    newPostSection.style.display = newPostSection.style.display === 'none' ? 'block' : 'none';
}

function createRaindrops() {
    const rain = document.getElementById('rain');
    const raindropsCount = 100;
    rain.innerHTML = '';

    for (let i = 0; i < raindropsCount; i++) {
        const drop = document.createElement('div');
        drop.classList.add('raindrop');
        drop.style.left = `${Math.random() * 100}%`;
        drop.style.animationDuration = `${0.5 + Math.random() * 0.5}s`;
        drop.style.animationDelay = `${Math.random() * 2}s`;
        rain.appendChild(drop);
    }
}

function createLightning() {
    const lightning = document.getElementById('lightning');
    setInterval(() => {
        if (Math.random() > 0.97) {
            lightning.style.opacity = 1;
            setTimeout(() => {
                lightning.style.opacity = 0;
            }, 50);
            setTimeout(() => {
                lightning.style.opacity = 0.5;
            }, 100);
            setTimeout(() => {
                lightning.style.opacity = 0;
            }, 150);
        }
    }, 100);
}

document.getElementById('newPostBtn').addEventListener('click', toggleNewPostForm);
document.getElementById('postForm').addEventListener('submit', (e) => {
    e.preventDefault();
    const title = document.getElementById('postTitle').value;
    const author = document.getElementById('postAuthor').value;
    const content = document.getElementById('postContent').value;
    createPost(title, author, content);
});

window.addEventListener('load', () => {
    fetchPosts();
    createRaindrops();
    createLightning();
});

