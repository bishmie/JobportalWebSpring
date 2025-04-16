function loadJobCategories() {
    fetch('http://localhost:8090/jobType/getAll')
        .then(response => response.json())
        .then(jobCategories => {
            const cardsContainer = document.getElementById('jobCategoryCards');
            cardsContainer.innerHTML = '';

            const iconMap = {
                'Garment': 'lni-tshirt',
                'IT': 'lni-code',
                'Health': 'lni-heart',
                'Food': 'lni-restaurant',
                'Educational': 'lni-graduation',
                'Construction': 'lni-construction',
                'Engineering': 'lni-cog',
                'Civil': 'lni-apartment',
                'Fashion': 'lni-tshirt',
                'Mechanical': 'lni-cogs',  // FIXED
                'hotel': 'lni-apartment',
            };


            jobCategories.forEach(category => {
                const card = document.createElement('div');
                card.classList.add('col-lg-3', 'col-md-6', 'col-12');
                card.classList.add('wow', 'fadeInUp');
                card.setAttribute('data-wow-delay', '.2s');

                const iconClass = iconMap[category.name] || 'lni-default';

                card.innerHTML = `
                        <a href="browse-jobs.html" class="single-cat">
                            <div class="icon">
                                <i class="lni ${iconClass}"></i>
                            </div>
                            <h3>${category.name}</h3>
                        </a>
                    `;

                cardsContainer.appendChild(card);
            });
        })
        .catch(error => console.error('Error loading job categories:', error));
}

window.onload = loadJobCategories;
