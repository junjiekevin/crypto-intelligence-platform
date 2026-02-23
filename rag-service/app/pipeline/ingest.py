import pypdf
import os

class DocumentIngestor:
    @staticmethod
    def extract_text(file_path: str) -> str:
        """
        Extracts raw text from a PDF or TXT file.
        """
        if not os.path.exists(file_path):
            raise FileNotFoundError(f"File not found: {file_path}")

        file_extension = os.path.splitext(file_path)[1].lower()

        if file_extension == '.pdf':
            return DocumentIngestor._extract_pdf_text(file_path)
        elif file_extension == '.txt':
            return DocumentIngestor._extract_txt_text(file_path)
        else:
            raise ValueError(f"Unsupported file extension: {file_extension}")

    @staticmethod
    def _extract_pdf_text(file_path: str) -> str:
        text = ""
        try:
            with open(file_path, "rb") as f:
                reader = pypdf.PdfReader(f)
                for page in reader.pages:
                    content = page.extract_text()
                    if content:
                        text += content + "\n"
        except Exception as e:
            raise RuntimeError(f"Failed to extract text from PDF: {str(e)}")
        return text

    @staticmethod
    def _extract_txt_text(file_path: str) -> str:
        try:
            with open(file_path, "r", encoding="utf-8") as f:
                return f.read()
        except UnicodeDecodeError:
            # Fallback to latin-1 if utf-8 fails
            with open(file_path, "r", encoding="latin-1") as f:
                return f.read()
        except Exception as e:
            raise RuntimeError(f"Failed to extract text from TXT: {str(e)}")
