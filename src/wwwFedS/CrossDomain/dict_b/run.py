f = open('Keyword_is_property_cd.txt', 'r', encoding='utf-8')
f_out = open('Keyword_is_property_cd_clean.txt', 'w')
c=[]
sum=0
s=0
for line in f:
	s+=1
	if line not in c:
		c.append(line)
		print(line, file=f_out, end='')
		sum+=1
print(sum,s)
