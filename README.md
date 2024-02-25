# Employee Management

## Proqram təminatı ilə bağlı qeydlər

1. Postman üçün endpointləri docs/postman - dən götürmək olar.
2. Tapşırıqda göstərilən UI üçün http://localhost:8081/form istifadə edə bilərsiniz.
3. UI texnologiya olaraq Javascript, Jquery, Bootstrap, HTML və CSS istifadə olunub.
4. İstifadəçi adına görə axtarış zamanı tapşırıq üzrə səhifələmə əlavə olunub.
5. Test datalar üçün Spring Data JPA repository metod istifadə edilib, zamana görə LiquiBase data miqrasiya istifadə edə bilmədim.
6. Departament adına və sistemdəki tapşırıqların əlavəoluna tarixinə görə axtarış minimal 2 gün intervalla əlavə olunub (Test data).
7. Cari proqramda login səhifəsi hazırlamadım, bunun əvəzinə daha tez olması üçün users cədvəlinə əlavə olunmuş istifadəçilərdən birini (Tural:123456) istifadə edərək Basic Auth növündə təhlükəsizlik tədbiri gördüm və bunu back-end sorğularında istifadə etdim.
8. Tapşırıq üzrə UI-da axtarışın iki növü təqdim olunub:

   a. İstifadəçi adına görə bitmiş tapşırıqlar
   
   b. Departament adına və tapşırığın əlavəolunma tarixi intervalına görə axtarış (Test departament adı olaraq: Programming götürülüb)
## Contributing

Pull requests are welcome. For major changes, please open an issue first
to discuss what you would like to change.


## License

[MIT](https://choosealicense.com/licenses/mit/)