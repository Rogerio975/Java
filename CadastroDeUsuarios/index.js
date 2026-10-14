const readline = require('readline-sync');

// Função para calcular a idade exata com base na data de nascimento (DD/MM/AAAA)
function calcularIdade(dataNascimentoStr) {
  const partes = dataNascimentoStr.split('/');
  if (partes.length !== 3) return null;

  const dia = parseInt(partes[0], 10);
  const mes = parseInt(partes[1], 10) - 1; // Meses em JS variam de 0 a 11
  const ano = parseInt(partes[2], 10);

  const dataNasc = new Date(ano, mes, dia);
  const hoje = new Date();

  // Verifica se a data fornecida é válida
  if (isNaN(dataNasc.getTime())) return null;

  let idade = hoje.getFullYear() - dataNasc.getFullYear();
  const diferencaMeses = hoje.getMonth() - dataNasc.getMonth();

  // Ajusta a idade se o aniversário ainda não tiver ocorrido no ano corrente
  if (diferencaMeses < 0 || (diferencaMeses === 0 && hoje.getDate() < dataNasc.getDate())) {
    idade--;
  }

  return idade;
}

function cadastrarUsuario() {
  console.log('=== CADASTRO DE USUÁRIO ===\n');

  const nome = readline.question('Nome completo: ');
  const email = readline.question('E-mail: ');
  const endereco = readline.question('Endereço: ');

  let dataNascimento;
  let idade;

  // Validação simples para garantir que a data fornecida gera uma idade válida
  while (true) {
    dataNascimento = readline.question('Data de nascimento (DD/MM/AAAA): ');
    idade = calcularIdade(dataNascimento);

    if (idade !== null && idade >= 0) {
      break;
    }
    console.log('⚠️ Data inválida! Por favor, use o formato DD/MM/AAAA com valores reais.\n');
  }

  console.log('\n==============================');
  console.log('    DADOS CADASTRADOS');
  console.log('==============================');
  console.log(`Nome:               ${nome}`);
  console.log(`E-mail:             ${email}`);
  console.log(`Endereço:           ${endereco}`);
  console.log(`Data de Nascimento: ${dataNascimento}`);
  console.log(`Idade Calculada:    ${idade} anos`);
  console.log('==============================\n');
}

cadastrarUsuario();